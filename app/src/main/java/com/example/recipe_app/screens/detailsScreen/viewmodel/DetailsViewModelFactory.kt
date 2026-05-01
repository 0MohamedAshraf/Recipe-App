package com.example.recipe_app.screens.detailsScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipe_app.database.dao.FavoriteDao
import com.example.recipe_app.screens.detailsScreen.repo.MealDetailsRepo
import com.example.recipe_app.screens.homeScreen.viewmodel.MealViewModel
import com.example.recipe_app.service.AccountService

class DetailsViewModelFactory(
    private val detailsRepo: MealDetailsRepo,
    private val accountService: AccountService,
    private val favoriteDao: FavoriteDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MealDetailsViewModel::class.java)){
            MealDetailsViewModel(detailsRepo,accountService,favoriteDao) as T
        }else{
            throw IllegalArgumentException("Error Creating Meal View Model")
        }
    }
}