package com.example.serviceapp.service

import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import com.example.serviceapp.R


class PlayAudioService : Service() {
    val TAG = "svv"
    private lateinit var mediaPlayer: MediaPlayer
    private val mBinder: LocalBinder = LocalBinder()

    inner class LocalBinder : Binder() {
        fun getService(): PlayAudioService = this@PlayAudioService
    }

    override fun onCreate() {
        super.onCreate()
        initMediaPlayer()
        Log.d(TAG, "onCreate: ")
    }

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    fun playAudio() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.sncp)
        mediaPlayer.start()
    }

    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer()
        mediaPlayer.setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
    }
}
