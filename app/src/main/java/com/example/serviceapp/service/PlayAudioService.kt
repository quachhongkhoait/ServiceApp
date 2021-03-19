package com.example.serviceapp.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.*
import androidx.core.app.NotificationCompat
import com.example.serviceapp.R
import com.example.serviceapp.model.Music
import com.example.serviceapp.utils.CallService


class PlayAudioService : Service(), CallService {
    val TAG = "svv"

    private val mHandler = Handler()
    val mList = arrayListOf(
        Music(
            R.raw.wmip,
            "Wrap Me In Plastic",
            "CHROMANCE, Marcus Layton",
            "https://sal.vn/q2PkYw"
        ),
        Music(R.raw.sck, "Sao cha không", "Phan Mạnh Quỳnh", "https://sal.vn/6Ng14M"),
        Music(R.raw.kbtl, "Khác biệt to lớn", "Trịnh Thăng Bình, Liz", "https://sal.vn/q2PkYw"),
        Music(R.raw.sncp, "Siêu nhân cuồng phong", "Takeshi", "https://sal.vn/172yqJ")
    )
    private lateinit var mediaPlayer: MediaPlayer
    var position: Int = 0
    private val mBinder: LocalBinder = LocalBinder()

    inner class LocalBinder : Binder() {
        fun getService(): PlayAudioService = this@PlayAudioService
    }

    override fun onCreate() {
        super.onCreate()
        initMediaPlayer()
        startNotification()
    }

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    fun playAudio(selectedTrack: Int) {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer = MediaPlayer.create(applicationContext, mList[selectedTrack].id)
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
            nextMusic(1)
        }
    }

    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer()
        mediaPlayer.setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
    }

    override fun getMessage(name: String): String {
        TODO("Not yet implemented")
    }

    override fun getResult(val1: Int, val2: Int): Int {
        TODO("Not yet implemented")
    }

    override fun playMusic(selectedTrack: Int) {
        position = selectedTrack
        playAudio(selectedTrack)
        //        val mRunnable = object : Runnable {
        //            override fun run() {
        //                mHandler.postDelayed(this, 1000)
        //            }
        //        }
        //        mRunnable.run()
    }


    override fun pauseMusic(selectedTrack: Int) {

    }

    override fun stopMusic(selectedTrack: Int) {
    }

    override fun nextMusic(selectedTrack: Int) {
        position += 1
        if (position >= mList.size) {
            position = 0
        } else {
            playAudio(position)
        }
    }

    override fun previousMusic(selectedTrack: Int) {
        position -= 1
        if (position < 0) {
            position = mList.size
        } else {
            playAudio(position)
        }
    }

    private fun startNotification() {
        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                "CHANNEL_ID",
                "My Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
            channel
        )
        val notification: Notification = NotificationCompat.Builder(this, "CHANNEL_ID")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(getString(R.string.app_name))
            .setContentText("Hi this is a media notification")
//            .setStyle(androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0))
            .setContentText("Generating random number").build()
        startForeground(1, notification)
    }
}
