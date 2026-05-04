package com.example.recipe_app.screens.splashScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipe_app.service.AccountService

class SplashViewModelFactory (
    private val service: AccountService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SplashViewModel::class.java)){
            SplashViewModel(service) as T
        }else{
            throw IllegalArgumentException("Error Creating Meal View Model")
        }
    }
}