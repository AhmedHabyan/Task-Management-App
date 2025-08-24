package com.example.taskmanagement.data.di

import com.example.core.task_room.TaskDao
import com.example.core.task_room.TaskDatabase
import com.example.taskmanagement.data.LocalDataSource
import com.example.taskmanagement.data.RepoImpl
import com.example.taskmanagement.domain.contract.Repo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideRepoImpl(localDataSource: LocalDataSource):Repo{
        return RepoImpl(localDataSource = localDataSource)
    }
    @Provides
    @Singleton
    fun provideLocalDataSource(taskDatabase:TaskDatabase):LocalDataSource{
        return LocalDataSource(taskDatabase)
    }

}