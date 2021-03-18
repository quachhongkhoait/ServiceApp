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
import kotlinx.android.synthetic.main.activity_main2.*
import java.lang.Exception

class MainActivity2 : AppCompatActivity() {

    private var messenger: Messenger? = null
    private val greeterServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, iBinder: IBinder?) {
            Log.d("nnn", "Connected to MainActivity2")
            try {
                messenger = Messenger(iBinder)
            }catch (ex : Exception){
                Log.d("nnn", "onServiceConnected: ${ex.message}")
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d("nnn", "Disconnected from MainActivity2")
            messenger = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        mBtnClick.setOnClickListener {
            val message: Message = Message.obtain(null, GreeterService.MSG_SAY_HELLO, 0, 0)
            messenger?.send(message)
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, GreeterService::class.java).also { intent ->
            bindService(intent, greeterServiceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(greeterServiceConnection)
    }
}