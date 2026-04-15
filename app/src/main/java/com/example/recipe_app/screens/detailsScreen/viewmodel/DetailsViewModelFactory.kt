package com.example.recipe_app.screens.detailsScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipe_app.screens.detailsScreen.repo.MealDetailsRepo
import com.example.recipe_app.screens.homeScreen.viewmodel.MealViewModel

class DetailsViewModelFactory(
    private val detailsRepo: MealDetailsRepo
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MealDetailsViewModel::class.java)){
            MealDetailsViewModel(detailsRepo) as T
        }else{
            throw IllegalArgumentException("Error Creating Meal View Model")
        }
    }
}