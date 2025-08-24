package com.example.corefeatures.notification

data class NotificationContent (
    val smallIcon:Int,
    val contentTitle:String,
    val contextText:String,
    val priority:Int
)