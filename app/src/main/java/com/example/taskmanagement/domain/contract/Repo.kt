package com.example.taskmanagement.domain.contract

import com.example.taskmanagement.data.utils.ApiResult
import com.example.taskmanagement.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface Repo {
    suspend fun getAllTasks(): Flow<ApiResult<List<Task>>>

    suspend fun insertTask(task:Task)

    suspend fun deleteTask(task:Task)

    suspend fun updateTask(task:Task)
}