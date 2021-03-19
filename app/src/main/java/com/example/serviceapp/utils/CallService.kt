package com.example.serviceapp.utils

interface CallService {
    fun getMessage(name: String): String
    fun getResult(val1: Int, val2: Int): Int
    fun playMusic(selectedTrack: Int)
    fun pauseMusic(selectedTrack: Int)
    fun stopMusic(selectedTrack: Int)
    fun nextMusic(selectedTrack: Int)
    fun previousMusic(selectedTrack: Int)
}