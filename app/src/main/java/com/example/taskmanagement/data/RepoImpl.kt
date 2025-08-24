package com.example.taskmanagement.data

import com.example.taskmanagement.data.utils.ApiResult
import com.example.taskmanagement.data.utils.safeCallApi
import com.example.taskmanagement.domain.contract.Repo
import com.example.taskmanagement.domain.model.Task
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class RepoImpl @Inject constructor(
    private val localDataSource:LocalDataSource
):Repo {
    override suspend fun getAllTasks(): Flow<ApiResult<List<Task>>> {
        return safeCallApi { localDataSource.getAllTasks()}
    }

    override suspend fun insertTask(task: Task) {
        localDataSource.insertTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        localDataSource.deleteTask(task)
    }

    override suspend fun updateTask(task: Task) {
       localDataSource.updateTask(task) 

    }
}