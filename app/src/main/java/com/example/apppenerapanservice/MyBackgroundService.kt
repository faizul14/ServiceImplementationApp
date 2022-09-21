package com.example.apppenerapanservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MyBackgroundService : Service() {
    companion object{
        const val TAG = "MyBackgroundService"
    }

    private val servceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + servceJob)

    override fun onBind(intent: Intent): IBinder {
        throw UnsupportedOperationException("not yet implemnetation")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG,  "Service Di Jalankan")

        serviceScope.launch {
            for (i in 1..10){
                delay(1000)
                Log.d(TAG,  "something $i")
            }
            stopSelf()
            Log.d(TAG,  "Service Di Hentikan")
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        servceJob.cancel()
        Log.d(TAG,  "OnDestroy: Service Di Hentikan")
    }
}