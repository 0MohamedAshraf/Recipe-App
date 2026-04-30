package com.example.recipe_app.screens.searchScreen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.network.ApiClient
import com.example.recipe_app.network.RemoteDataSourceImpl
import com.example.recipe_app.screens.homeScreen.dto.Meal
import kotlinx.coroutines.launch

class MealsListViewModel : ViewModel() {

    private val remoteDataSource = RemoteDataSourceImpl(ApiClient.service)

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
                    "category"   -> remoteDataSource.filterByCategory(filterValue)
                    "country"    -> remoteDataSource.filterByCountry(filterValue)
                    "ingredient" -> remoteDataSource.filterByIngredient(filterValue)
                    else         -> null
                }
                allMeals = response?.body()?.meals ?: emptyList()
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