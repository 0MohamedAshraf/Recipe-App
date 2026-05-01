package com.example.recipe_app.screens.favScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipe_app.database.dao.FavoriteDao
import com.example.recipe_app.service.AccountService

class FavoriteViewModelFactory(
    private val favoriteDao: FavoriteDao,
    private val accountService: AccountService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
            FavoriteViewModel(favoriteDao,accountService) as T
        }else{
            throw IllegalArgumentException("Error Creating Meal View Model")
        }
    }
}