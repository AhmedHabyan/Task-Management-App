package com.example.taskmanagement.domain.contract

import com.example.core.task_room.TaskEntity
import com.example.taskmanagement.domain.model.Task

interface DataSource {
    suspend fun getAllTasks():List<Task>

    suspend fun insertTask(task:Task)

    suspend fun deleteTask(task:Task)

    suspend fun updateTask(task:Task)
}