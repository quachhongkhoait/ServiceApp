package com.example.serviceapp.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log
import com.example.serviceapp.R
import com.example.serviceapp.service.GreeterService
import com.example.serviceapp.service.PlayAudioService
import kotlinx.android.synthetic.main.activity_main2.*
import java.lang.Exception

class MainActivity2 : AppCompatActivity() {
    val TAG = "svv"
    private lateinit var mPlayAudioService: PlayAudioService

    private val playmusicServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "onServiceConnected: MainActivity2")
            val binder = service as PlayAudioService.LocalBinder
            mPlayAudioService = binder.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected: MainActivity2")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        mBtnClick.setOnClickListener {
            Intent(this, PlayAudioService::class.java).also { intent ->
                bindService(intent, playmusicServiceConnection, Context.BIND_INCLUDE_CAPABILITIES)
            }
        }
        mBtnPlay.setOnClickListener {
            Intent(this, PlayAudioService::class.java).also { intent ->
                bindService(intent, playmusicServiceConnection, Context.BIND_AUTO_CREATE)
            }
        }
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onStop() {
        super.onStop()
//        unbindService(playmusicServiceConnection)
    }
}