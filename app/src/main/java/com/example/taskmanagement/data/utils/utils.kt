package com.example.taskmanagement.data.utils

import com.example.corefeatures.task_room.TaskEntity
import com.example.taskmanagement.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


suspend fun<T> safeCallApi(callApi:suspend ()->T): Flow<ApiResult<T>> {

    return flow {
        emit(ApiResult.Loading)
        try {
            val response = callApi.invoke()
            emit(ApiResult.Success(response))
        } catch (exception: Exception) {
            emit(ApiResult.Failed(exception))
        }
    }
}

fun TaskEntity.toTask(): Task {
    return Task(
        id=id,
        title=title,
        description=description,
        status=status,
        statusColor = statusColor
    )
}