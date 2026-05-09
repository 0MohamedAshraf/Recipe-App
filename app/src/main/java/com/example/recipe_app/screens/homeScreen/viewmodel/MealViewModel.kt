package com.example.recipe_app.screens.homeScreen.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.database.dao.FavoriteDao
import com.example.recipe_app.database.entity.Favorite
import com.example.recipe_app.screens.homeScreen.dto.Category
import com.example.recipe_app.screens.homeScreen.dto.Meal
import com.example.recipe_app.screens.homeScreen.repo.MealRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MealViewModel(
    private val mealRepository: MealRepository,
    private val favoriteDao: FavoriteDao
) : ViewModel() {

    init {
        getRandomMeal()
        getAllCategories()
        getMealByCategory("Beef")
    }

    private val _isOffline = MutableStateFlow(false)
    val isOffline = _isOffline.asStateFlow()
    private val _randomMeal = MutableStateFlow<Meal?>(null)
    val randomMeal = _randomMeal.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _selectedCategoryIndex = MutableStateFlow(0)
    val selectedCategoryIndex = _selectedCategoryIndex.asStateFlow()

    private val _categoryOfMeals = MutableStateFlow<List<Meal>>(emptyList())
    val categoryOfMeals = _categoryOfMeals.asStateFlow()

    private val _favIds = MutableStateFlow(emptyList<String>())
    val favIds = _favIds.asStateFlow()

    fun setSelectedCategoryIndex(index: Int) {
        _selectedCategoryIndex.value = index
    }
    fun getAllCategories(){
        viewModelScope.launch {
            try {
                val response = mealRepository.getAllCategories()
                if (response.isSuccessful){
                    val categoriesList = response.body()?.categories
                    if (categoriesList != null) {
                        _categories.value = categoriesList
                        Log.d("asd --> ", "getCategories: ${categoriesList.joinToString(",")}")

                    }else{
                        Log.d("asd --> ", "getCategories: error")

                    }
                    _isOffline.value = false
                }
            }catch (e: Exception){
                _isOffline.value = true
            }
        }
    }
    fun getRandomMeal(){
        viewModelScope.launch {
            try{
            val response = mealRepository.getRandomMeal()
            if(response.isSuccessful){
                val meal = response.body()?.meals?.get(0)
                _randomMeal.value = meal
                Log.d("asd --> ", "getMeal: $meal")
                _isOffline.value = false
            }
            else
                Log.d("asd -->", "getMeal: error ")
            }catch (e: Exception){
                _isOffline.value = true
            }
        }
    }

    fun addToFavorite(favorite: Favorite){
        viewModelScope.launch(Dispatchers.IO){
            favoriteDao.addFavorite(favorite)
            getFavMeals()
        }
    }

    fun getFavMeals(){
        viewModelScope.launch(Dispatchers.IO){
            val favs = favoriteDao.getAll(Firebase.auth.currentUser?.uid.orEmpty())
            _favIds.value = favs.map { it.id }

        }
    }
    fun removeFromFav(mealId: String){
        viewModelScope.launch(Dispatchers.IO){
            favoriteDao.removeFavorite(
                userId = Firebase.auth.currentUser?.uid.orEmpty(),
                mealId
            )
            getFavMeals()
        }
    }
    fun getMealByCategory(category: String){
        viewModelScope.launch {
            try{
                val response = mealRepository.filterByCategory(category)
                if(response.isSuccessful){
                    val ListOfMeals = response.body()?.meals
                    if (ListOfMeals != null)
                    _categoryOfMeals.value = ListOfMeals
                    else Log.d("asd -->", "getMealByCategory: Empty List of Meals ")
                    _isOffline.value = false

                }else{
                    Log.d("asd -->", "getMealByCategory: Error")
                }
            }catch (e: Exception){
                _isOffline.value = true
            }
        }
    }

}