package com.example.corefeatures.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object NotificationHelper {
    private val CHANNEL_ID= "Task Channel Id"
    fun createNotificationChannel(context:Context){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel= NotificationChannel(
                 CHANNEL_ID,
                "Task notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showNotification(context: Context, notificationContent: NotificationContent){
        val notificationId = 10
            val notification= NotificationCompat.Builder(
                context,
                CHANNEL_ID
            )

        notification.setContentText(notificationContent.contextText)
            .setContentTitle(notificationContent.contentTitle)
            .setSmallIcon(notificationContent.smallIcon)
            .setPriority(notificationContent.priority)

        NotificationManagerCompat.from(context).notify(notificationId,notification.build())
    }
}