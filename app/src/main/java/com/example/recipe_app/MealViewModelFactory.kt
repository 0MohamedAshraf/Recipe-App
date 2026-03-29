package com.example.recipe_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipe_app.repo.MealRepository

class MealViewModelFactory(
    private val repository: MealRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MealViewModel::class.java)){
            MealViewModel(repository) as T
        }else{
            throw IllegalArgumentException("Error Creating Meal View Model")
        }
    }
}