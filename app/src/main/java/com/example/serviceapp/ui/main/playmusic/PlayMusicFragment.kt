package com.example.serviceapp.ui.main.playmusic

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.serviceapp.R
import com.example.serviceapp.utils.OnClickListener
import kotlinx.android.synthetic.main.play_music_fragment.*

class PlayMusicFragment : Fragment() {

    lateinit var listener: OnClickListener

    companion object {
        fun newInstance() = PlayMusicFragment()
    }

    private lateinit var viewModel: PlayMusicViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.play_music_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnClickListener
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlayMusicViewModel::class.java)

        onClick()
    }

    private fun onClick() {
        mIVBack.setOnClickListener {
            listener.onClick(false)
        }
    }
}