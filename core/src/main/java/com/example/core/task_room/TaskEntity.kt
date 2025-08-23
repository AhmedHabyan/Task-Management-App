package com.example.core.task_room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class TaskEntity (
    @PrimaryKey
    val id:Int,

    @ColumnInfo("title")
    val title:String,

    @ColumnInfo("description")
    val description:String,

    @ColumnInfo("status")
    val status:String,


    )