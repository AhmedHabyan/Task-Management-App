package com.example.taskmanagement.presentation.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.example.corefeatures.notification.NotificationContent
import com.example.corefeatures.notification.NotificationHelper
import com.example.taskmanagement.R

fun NavController.navigateSafeDirections(destinationId:Int,directions: NavDirections){
    if(currentDestination?.id != destinationId){
        navigate(directions)
    }
}

fun NavController.navigateSafe(destinationId:Int){
    if(currentDestination?.id != destinationId){
        navigate(destinationId)
    }
}

fun View.setVisiblity(){
    isVisible=true
}

fun View.clearVisiblity(){
    isVisible= false
}

fun showCurrentNotification(context: Context,title:String,description:String) {
    if(ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED){
        NotificationHelper.createNotificationChannel(context)
        NotificationHelper.showNotification(context,
            NotificationContent(
                smallIcon = R.drawable.ic_launcher_background,
                contentTitle = title,
                contextText = description,
                priority = NotificationCompat.PRIORITY_HIGH
            )
        )
    }
}