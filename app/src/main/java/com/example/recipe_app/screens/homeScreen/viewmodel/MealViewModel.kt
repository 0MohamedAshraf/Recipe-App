package com.example.recipe_app.screens.homeScreen.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.screens.homeScreen.dto.Category
import com.example.recipe_app.screens.homeScreen.dto.Meal
import com.example.recipe_app.screens.homeScreen.repo.MealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MealViewModel(
    private val mealRepository: MealRepository
) : ViewModel() {

    init {
        getRandomMeal()
        getAllCategories()
    }
    private val _randomMeal = MutableStateFlow<Meal?>(null)
    val randomMeal = _randomMeal.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _categoryOfMeals = MutableStateFlow<List<Meal>>(emptyList())
    val categoryOfMeals = _categoryOfMeals.asStateFlow()
    fun getAllCategories(){
        viewModelScope.launch {
            val response = mealRepository.getAllCategories()
            if (response.isSuccessful){
                val categoriesList = response.body()?.categories
                if (categoriesList != null) {
                    _categories.value = categoriesList
                    Log.d("asd --> ", "getCategories: ${categoriesList.joinToString(",")}")

                }else{
                    Log.d("asd --> ", "getCategories: error")

                }
            }
        }
    }
    fun getRandomMeal(){
        viewModelScope.launch {
            val response = mealRepository.getRandomMeal()
            if(response.isSuccessful){
                val meal = response.body()?.meals?.get(0)
                _randomMeal.value = meal
                Log.d("asd --> ", "getMeal: $meal")
            }
            else
                Log.d("asd -->", "getMeal: error ")
        }
    }

    fun getMealByCategory(category: String){
        viewModelScope.launch {
            val response = mealRepository.filterByCategory(category)
            if(response.isSuccessful){
                val ListOfMeals = response.body()?.meals
                if (ListOfMeals != null)
                _categoryOfMeals.value = ListOfMeals
                else Log.d("asd -->", "getMealByCategory: Empty List of Meals ")
            }else{
                Log.d("asd -->", "getMealByCategory: Error")
            }
        }
    }

}