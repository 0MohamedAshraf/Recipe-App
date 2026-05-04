package com.example.recipe_app.screens.homeScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipe_app.database.dao.FavoriteDao
import com.example.recipe_app.screens.homeScreen.repo.MealRepository

class MealViewModelFactory(
    private val repository: MealRepository,
    private val favoriteDao: FavoriteDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MealViewModel::class.java)){
            MealViewModel(repository,favoriteDao) as T
        }else{
            throw IllegalArgumentException("Error Creating Meal View Model")
        }
    }
}