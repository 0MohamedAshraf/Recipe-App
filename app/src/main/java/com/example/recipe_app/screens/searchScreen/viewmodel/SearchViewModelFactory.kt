package com.example.recipe_app.screens.searchScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipe_app.screens.homeScreen.viewmodel.MealViewModel
import com.example.recipe_app.screens.searchScreen.repo.SearchRepository

class SearchViewModelFactory(
    private val repository: SearchRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SearchViewModel::class.java)){
            SearchViewModel(repository) as T
        }else{
            throw IllegalArgumentException("Error Creating Meal View Model")
        }
    }
}