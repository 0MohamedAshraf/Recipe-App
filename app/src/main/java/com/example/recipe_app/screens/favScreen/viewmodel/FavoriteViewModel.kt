package com.example.recipe_app.screens.favScreen.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.database.dao.FavoriteDao
import com.example.recipe_app.database.entity.Favorite
import com.example.recipe_app.service.AccountService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class FavoriteViewModel(
    private val favoriteDao: FavoriteDao,
    private val accountService: AccountService
) : ViewModel() {

    init {
        getAllFavorites()
    }
    private val _favoriteMeals  = MutableStateFlow<List<Favorite>>(emptyList())
    val favoriteMeals = _favoriteMeals.asStateFlow()

    fun getAllFavorites(){
        viewModelScope.launch(Dispatchers.IO) {
            val favs = favoriteDao.getAll(accountService.currentUserId)
            _favoriteMeals.value = favs
            Log.d("abc --> ", "getAllFavorites: $favs")

        }
    }


    fun removeFavorite(mealId: String) {
        viewModelScope.launch(Dispatchers.IO){
            favoriteDao.removeFavorite(
                userId = accountService.currentUserId,
                mealId = mealId
            )
            getAllFavorites()
        }
    }

}