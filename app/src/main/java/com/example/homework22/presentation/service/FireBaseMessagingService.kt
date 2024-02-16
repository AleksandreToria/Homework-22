package com.example.homework22.presentation.service

import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.example.homework22.R
import com.example.homework22.presentation.activity.MainActivity
import com.google.android.datatransport.runtime.logging.Logging.d
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        d("MessageReceived", "${message.data}")

        val id = message.data["id"]?.toIntOrNull() ?: 0
        val desc = message.data["desc"] ?: "No Description"
        val title = message.notification?.title ?: "Title"
        val messageBody = message.notification?.body ?: "Message Body"

        sendNotification(title, messageBody, id, desc)
    }

    private fun sendNotification(title: String, messageBody: String, id: Int, desc: String) {
        val args = Bundle().apply {
            putInt("id", id)
            putString("desc", desc)
        }

        val pendingIntent = NavDeepLinkBuilder(this)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.postDetailFragment)
            .setArguments(args)
            .createPendingIntent()

        val channelId = getString(R.string.channel_id)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, channelId).apply {
            setSmallIcon(R.drawable.ic_launcher_background)
            setContentTitle(title)
            setContentText(messageBody)
            setAutoCancel(true)
            setSound(defaultSoundUri)
            setContentIntent(pendingIntent)
        }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())
    }


    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}
