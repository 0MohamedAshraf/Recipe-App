package com.example.recipe_app.screens.searchScreen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.screens.homeScreen.dto.Meal
import com.example.recipe_app.screens.searchScreen.repo.SearchRepository
import kotlinx.coroutines.launch

class MealsListViewModel(
    private val repository: SearchRepository
) : ViewModel() {

    var meals by mutableStateOf<List<Meal>>(emptyList())
        private set
    var allMeals by mutableStateOf<List<Meal>>(emptyList())
        private set
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun loadMeals(filterType: String, filterValue: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val response = when (filterType) {
                    "category"   -> repository.filterByCategory(filterValue).body()?.meals
                    "country"    -> repository.filterByCountry(filterValue).body()?.meals
                    "ingredient" -> repository.filterByIngredient(filterValue).body()?.meals
                    else         -> emptyList<Meal>()
                }
                allMeals = response ?: emptyList()
                meals = allMeals
                if (meals.isEmpty()) errorMessage = "No meals found!"
            } catch (e: Exception) {
                errorMessage = "Something went wrong!"
            } finally {
                isLoading = false
            }
        }
    }

    fun filterMeals(query: String) {
        meals = if (query.isBlank()) allMeals
        else allMeals.filter { it.strMeal.contains(query, ignoreCase = true) }
    }
}