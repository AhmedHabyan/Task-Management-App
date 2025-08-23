package com.example.taskmanagement.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    val title:String,
    val description:String,
    val status:String= "Pending"
):Parcelable