package com.example.core.task_room.di

import android.content.Context
import androidx.room.Room
import com.example.core.task_room.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object TaskRoomModule {

    @Provides
    fun provideTaskDatabase(
        @ApplicationContext context:Context
    ):TaskDatabase{
        return Room.databaseBuilder(
            context,
            TaskDatabase::class.java, "task_db"
        ).build()
    }
}