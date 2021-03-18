package com.example.serviceapp.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class CalculatorService : Service() {

    private val mBinder: LocalBinder = LocalBinder()

    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }

    inner class LocalBinder : Binder() {
        // Return this instance of BoundService so clients can call public methods
        fun getService(): CalculatorService = this@CalculatorService
    }

    // Methods for clients
    fun add(a: Int, b: Int) = a + b

    fun subtract(a: Int, b: Int) = a - b

    fun multiply(a: Int, b: Int) = a * b

    fun divide(a: Int, b: Int) = a / b

}