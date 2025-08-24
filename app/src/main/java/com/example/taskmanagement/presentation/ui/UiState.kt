package com.example.taskmanagement.presentation.ui

import com.example.taskmanagement.domain.model.Task

sealed class UiState{
    object Ideal:UiState()
    object Loading:UiState()
    data class Success(val tasks:List<Task>):UiState()
    data class Error(val errorMessage:String):UiState()
}