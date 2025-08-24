package com.example.taskmanagement.domain.model

import android.os.Parcelable
import com.example.core.task_room.TaskEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    val id:Int,
    val title:String,
    val description:String,
    val status:String= "Pending",
    val statusColor:String
):Parcelable
{
    fun toTaskEntity():TaskEntity{
        return TaskEntity(
            id=id,
            title = title,
            description = description,
            status = status,
            statusColor=statusColor
        )
    }
}