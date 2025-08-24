package com.example.taskmanagement.presentation.utils

import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDirections

fun NavController.navigateSafeDirections(destinationId:Int,directions: NavDirections){
    if(currentDestination?.id != destinationId){
        navigate(directions)
    }
}

fun NavController.navigateSafe(destinationId:Int){
    if(currentDestination?.id != destinationId){
        navigate(destinationId)
    }
}

fun View.setVisiblity(){
    isVisible=true
}

fun View.clearVisiblity(){
    isVisible= false
}