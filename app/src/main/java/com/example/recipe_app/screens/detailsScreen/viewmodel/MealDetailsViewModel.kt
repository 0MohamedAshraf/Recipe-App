package com.example.recipe_app.screens.detailsScreen.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.database.dao.FavoriteDao
import com.example.recipe_app.database.entity.Favorite
import com.example.recipe_app.screens.detailsScreen.repo.MealDetailsRepo
import com.example.recipe_app.screens.homeScreen.dto.Meal
import com.example.recipe_app.service.AccountService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MealDetailsViewModel(
    private val mealDetailsRepo: MealDetailsRepo,
    private val accountService: AccountService,
    private val favoriteDao: FavoriteDao
) : ViewModel(){

    private val _isOffline = MutableStateFlow(false)
    val isOffline = _isOffline.asStateFlow()
    private val _meal = MutableStateFlow<Meal?>(null)
    val meal = _meal.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    fun getMealById(id: String) {
        viewModelScope.launch {
            try {
                val response = mealDetailsRepo.getMealById(id)
                if (response.isSuccessful) {
                    _meal.value = response.body()?.meals?.get(0)
                    isMealFavorite()
                    _isOffline.value = false
                } else {
                    Log.d("abc --> ", "getMealById: didn't find meal")
                    _isOffline.value = true
                }
            } catch (e: Exception) {
                Log.e("abc --> ", "getMealById exception: ${e.message}")
                _isOffline.value = true
            }
        }
    }

    fun isMealFavorite(){
        viewModelScope.launch(Dispatchers.IO){
            _isFavorite.value = favoriteDao.isMealFavorited(_meal.value?.idMeal.orEmpty(),
                accountService.currentUserId)

        }
    }
    fun addToFavorites(){

        viewModelScope.launch(Dispatchers.IO) {
            val fav = Favorite(
                userId = accountService.currentUserId,
                id = _meal.value?.idMeal.orEmpty(),
                name = _meal.value?.strMeal.orEmpty(),
                image = _meal.value?.strMealThumb.orEmpty(),
                category = _meal.value?.strCategory.orEmpty(),
                area = _meal.value?.strArea.orEmpty()
            )

            favoriteDao.addFavorite(fav)
            _isFavorite.value = true

        }
    }
    fun removeFromFavorites(){
        viewModelScope.launch(Dispatchers.IO){
            val deleted = favoriteDao.removeFavorite(
                userId = accountService.currentUserId,
                mealId = _meal.value?.idMeal.orEmpty()
            )
            if( deleted != 0){
                _isFavorite.value = false
            }else{
                Log.d("abc --> ", "removeFromFavorites: no meal deleted")
            }

        }
    }

}