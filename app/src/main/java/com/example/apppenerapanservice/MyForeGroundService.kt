package com.example.apppenerapanservice

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*

class MyForeGroundService : Service() {
    companion object{
        private const val NOTIFICATION_ID = 1
        private const val CHANEL_ID = "chanel_id"
        private const val CHANEL_NAME = "sevice chanel"
        internal val TAG = MyForeGroundService::class.java.simpleName
    }

    val seviceJob = Job()
    val serviceScope = CoroutineScope(Dispatchers.Main + seviceJob)

    private fun buildNotification() : Notification{
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntentFlags : Int = if (Build.VERSION.SDK_INT >= 23){
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        }else{
            PendingIntent.FLAG_UPDATE_CURRENT
        }

        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, pendingIntentFlags)

        val mNotficationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificatuinBuilder = NotificationCompat.Builder(this, CHANEL_ID)
            .setContentTitle("ForeGround Service")
            .setContentText("Saat ini foreground service sedang berjalan")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val chanel = NotificationChannel(
                CHANEL_ID,
                CHANEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            chanel.description = CHANEL_NAME
            notificatuinBuilder.setChannelId(CHANEL_ID)
            mNotficationManager.createNotificationChannel(chanel)
        }

        return notificatuinBuilder.build()


    }

    override fun onBind(intent: Intent): IBinder {
        throw UnsupportedOperationException ("Not yet implentation")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = buildNotification()

        startForeground(NOTIFICATION_ID, notification)

        Log.d(TAG, "Service Di Jalankan")

        serviceScope.launch {
            for (i in 1..50){
                delay(1000)
                Log.d(TAG, "Something $i")
            }

            stopForeground(true)
            stopSelf()
            Log.d(TAG, "Service Di Hentikan")

        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        Log.d(TAG, "On Destroy : Service Di Hentikan")

    }
}