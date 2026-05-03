package com.example.recipe_app.screens.searchScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipe_app.screens.searchScreen.repo.SearchRepository

class MealsListVmFactory(
    private val repository: SearchRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MealsListViewModel::class.java)){
            MealsListViewModel(repository) as T
        }else{
            throw IllegalArgumentException("Error Creating Meal View Model")
        }
    }
}