package com.example.serviceapp.service

import android.app.PendingIntent
import android.app.Service
import android.content.ComponentName
import android.content.Context
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

    override fun onBind(intent: Intent): IBinder {
        initMediaPlayer()
        return mBinder
    }

    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sncp);
        mediaPlayer.start()
        Log.d(TAG, "initMediaPlayer: ")
    }
}


//class lcBind : Binder() {
//    internal// Return this instance of LocalService so clients can call public methods
//    val service: PlayAudioService
//        get() = this@PlayAudioService
//}
