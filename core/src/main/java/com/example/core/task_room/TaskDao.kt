package com.example.core.task_room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert
    fun insertTask(task:TaskEntity)

    @Update
    fun updateTask(task:TaskEntity)

    @Delete
    fun deleteTask(task:TaskEntity)

    @Query("select * from task")
    fun getAllTasks(): List<TaskEntity>
}