package com.example.taskmanagement.presentation.viewModel

import androidx.lifecycle.ViewModel

class ActivityViewModel:ViewModel() {

    private val _isEditMode:Boolean = false
    var isEditMode = _isEditMode
}