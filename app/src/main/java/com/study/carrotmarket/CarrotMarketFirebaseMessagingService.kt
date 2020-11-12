package com.study.carrotmarket;

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.study.carrotmarket.view.main.MainActivity

class CarrotMarketFirebaseMessagingService : FirebaseMessagingService() {
    private val TAG = "CarrotMarketFirebaseMessagingService"

    override fun onMessageReceived(remoteMsg: RemoteMessage) {
        Log.d(TAG, "onMessageReceived : ${remoteMsg.notification?.body}")

        remoteMsg.notification?.let {
            sendNotification(it.body)
        }
    }

    private fun sendNotification(body: String?) {
        val intent = Intent(this, MainActivity::class.java ).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("Notification", body)
        }

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, "Notification")
            .setSmallIcon(R.drawable.ic_baseline_mail_24)
            .setChannelId("0")
            .setContentTitle("Push FCM")
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(notificationSound)
            .setContentIntent(pendingIntent)

        val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())
    }

    override fun onNewToken(newToken: String) {
        Log.d(TAG, "onNewToken: $newToken")
    }
}
