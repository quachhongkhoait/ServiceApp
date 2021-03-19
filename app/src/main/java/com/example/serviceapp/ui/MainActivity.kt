package com.example.serviceapp.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.res.TypedArray
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.serviceapp.R
import com.example.serviceapp.service.PlayAudioService
import com.example.serviceapp.ui.main.dashboard.DashboardFragment
import com.example.serviceapp.ui.main.home.HomeFragment
import com.example.serviceapp.ui.main.notifications.NotificationsFragment
import com.example.serviceapp.ui.main.playmusic.PlayMusicFragment
import com.example.serviceapp.utils.OnClickListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), OnClickListener {
    val TAG = "main"


    private lateinit var mPlayAudioService: PlayAudioService

    private val playmusicServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "onServiceConnected: MainActivity")
            val binder = service as PlayAudioService.LocalBinder
            mPlayAudioService = binder.getService()

            onHover()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected: MainActivity")
        }

    }

    private fun onHover(): Int {
        val attrs = intArrayOf(R.attr.selectableItemBackground)
        val typedArray: TypedArray = obtainStyledAttributes(attrs)
        return typedArray.getResourceId(0, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewPager()
        initBottomNav()
        initFragment()
        bindService()
        onClick()
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.mFCWPlay, PlayMusicFragment())
            .commit()
    }

    private fun onClick() {
        mIVPlay.setBackgroundResource(onHover())
        mIVNext.setBackgroundResource(onHover())
        mIVPrevous.setBackgroundResource(onHover())

        mIVPlay.setOnClickListener {
            mPlayAudioService.playMusic(0)
        }
        mIVNext.setOnClickListener {
            mPlayAudioService.nextMusic(1)
        }
        mIVPrevous.setOnClickListener {
            mPlayAudioService.previousMusic(0)
        }
    }

    private fun fragmentPlay(b: Boolean) {
        mFCWPlay.visibility = if (b) View.VISIBLE else View.GONE
        mRltMain.visibility = if (b) View.GONE else View.VISIBLE
    }

    private fun initViewPager() {
        val mAdapterPager = AdapterPager(supportFragmentManager)
        mAdapterPager.addData(HomeFragment(), "Home")
        mAdapterPager.addData(DashboardFragment(), "Dashboard")
        mAdapterPager.addData(NotificationsFragment(), "Notifications")
        mViewPager.adapter = mAdapterPager
        mViewPager.offscreenPageLimit = 3
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                mBottomNav.menu.getItem(position).isChecked = true
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

    private fun initBottomNav() {
        mBottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    mViewPager.currentItem = 0
                    true
                }
                R.id.navigation_dashboard -> {
                    mViewPager.currentItem = 1
                    true
                }
                R.id.navigation_notifications -> {
                    mViewPager.currentItem = 2
                    true
                }
                else -> false
            }
        }
    }


    private fun bindService() {
        Intent(this, PlayAudioService::class.java).also { intent ->
            bindService(intent, playmusicServiceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    private fun unbindService() {
        Intent(this, PlayAudioService::class.java).also { intent ->
            unbindService(playmusicServiceConnection)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService()
    }

    override fun onClick(check: Boolean) {
        fragmentPlay(check)
    }

    fun openPlay(view: View) {
        fragmentPlay(true)
    }
}
