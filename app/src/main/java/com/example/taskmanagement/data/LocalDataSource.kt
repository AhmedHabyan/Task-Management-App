package com.example.taskmanagement.data


import com.example.core.task_room.TaskDatabase
import com.example.taskmanagement.data.utils.toTask
import com.example.taskmanagement.domain.contract.DataSource
import com.example.taskmanagement.domain.model.Task
import jakarta.inject.Inject

class LocalDataSource @Inject constructor(
    private val taskDatabase: TaskDatabase
) :DataSource{
    override suspend fun getAllTasks():List<Task>{
       return taskDatabase.taskDao().getAllTasks().map {
            it.toTask()
        }
    }

    override suspend fun insertTask(task: Task) {
        taskDatabase.taskDao().insertTask(task.toTaskEntity())
    }

    override suspend fun deleteTask(task: Task) {
        taskDatabase.taskDao().deleteTask(task.toTaskEntity())
    }

    override suspend fun updateTask(task: Task) {
        taskDatabase.taskDao().updateTask(task.toTaskEntity())
    }
}