package com.example.core.task_room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "task")
data class TaskEntity (
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,

    @ColumnInfo("title")
    val title:String,

    @ColumnInfo("description")
    val description:String,

    @ColumnInfo("status")
    val status:String,

    @ColumnInfo("statusColor")
    val statusColor:String

    )