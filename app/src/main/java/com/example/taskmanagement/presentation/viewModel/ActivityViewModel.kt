package com.example.taskmanagement.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanagement.data.utils.ApiResult
import com.example.taskmanagement.domain.contract.Repo
import com.example.taskmanagement.domain.model.Task
import com.example.taskmanagement.presentation.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val repo:Repo
):ViewModel() {

    private val _isEditMode:Boolean = false
    var isEditMode = _isEditMode

    private val _uiState= MutableStateFlow<UiState>(UiState.Ideal)
    var uiState= _uiState

    private val _updateState= MutableStateFlow<UiState>(UiState.Ideal)
    var updateState=_updateState

    fun getAllTasks(){
        _uiState.value= UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllTasks().collect {
                when(it){
                    is ApiResult.Failed -> {
                        _uiState.value =  UiState.Error(it.error.message ?: "something went wrong")
                    }
                    ApiResult.Loading -> {
                        _uiState.value= UiState.Loading
                    }
                    is ApiResult.Success -> {
                        _uiState.value = UiState.Success(it.response)
                    }
                }
            }
        }
    }

    fun insertTask(task:Task, onComplete: () -> Unit={}){
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertTask(task)
        }.invokeOnCompletion {
            viewModelScope.launch{
                withContext(Dispatchers.Main){
                    onComplete()
                }
            }
        }
    }
    fun deleteTask(task:Task){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteTask(task)
        }
    }

    fun updateTask(task:Task,onComplete:()->Unit={}){
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateTask(task)
        }.invokeOnCompletion {
            viewModelScope.launch{
                withContext(Dispatchers.Main){
                    onComplete()
                }
            }

        }
    }

}