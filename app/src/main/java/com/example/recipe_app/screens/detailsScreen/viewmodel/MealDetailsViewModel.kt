package com.example.recipe_app.screens.detailsScreen.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.screens.detailsScreen.repo.MealDetailsRepo
import com.example.recipe_app.screens.homeScreen.dto.Meal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MealDetailsViewModel(
    private val mealDetailsRepo: MealDetailsRepo
) : ViewModel(){

    private val _meal = MutableStateFlow<Meal?>(null)
    val meal = _meal.asStateFlow()

    fun getMealById(id: String){
        viewModelScope.launch {
            val response = mealDetailsRepo.getMealById(id)
            if(response.isSuccessful){
                _meal.value = response.body()?.meals?.get(0)
                Log.d("abc --> ", "details view model: {${_meal.value?.idMeal}}")
            }else Log.d("abc --> ", "getMealById: didn't find meal")
        }
    }

}