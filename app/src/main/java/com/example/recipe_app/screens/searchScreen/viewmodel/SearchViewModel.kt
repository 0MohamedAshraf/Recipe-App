package com.example.recipe_app.screens.searchScreen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.network.ApiClient
import com.example.recipe_app.network.RemoteDataSourceImpl
import com.example.recipe_app.screens.homeScreen.dto.Category
import com.example.recipe_app.screens.homeScreen.dto.Meal
import com.example.recipe_app.screens.searchScreen.dto.Country
import com.example.recipe_app.screens.searchScreen.dto.Ingredient
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val remoteDataSource = RemoteDataSourceImpl(ApiClient.service)

    var searchResults by mutableStateOf<List<Meal>>(emptyList())
        private set
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set
    private var searchJob: Job? = null

    var allCategories by mutableStateOf<List<Category>>(emptyList())
        private set
    var filteredCategories by mutableStateOf<List<Category>>(emptyList())
        private set

    var allCountries by mutableStateOf<List<Country>>(emptyList())
        private set
    var filteredCountries by mutableStateOf<List<Country>>(emptyList())
        private set

    var allIngredients by mutableStateOf<List<Ingredient>>(emptyList())
        private set
    var filteredIngredients by mutableStateOf<List<Ingredient>>(emptyList())
        private set

    var isTabsLoading by mutableStateOf(false)
        private set

    init {
        loadAllTabsData()
    }

    private fun loadAllTabsData() {
        viewModelScope.launch {
            isTabsLoading = true
            try {
                val categories = remoteDataSource.getAllCategories()
                allCategories = categories.body()?.categories ?: emptyList()
                filteredCategories = allCategories

                val countries = remoteDataSource.getAllCountries()
                allCountries = countries.body()?.meals ?: emptyList()
                filteredCountries = allCountries

                val ingredients = remoteDataSource.getAllIngredients()
                allIngredients = ingredients.body()?.meals ?: emptyList()
                filteredIngredients = allIngredients
            } catch (e: Exception) {
                errorMessage = "Failed to load data!"
            } finally {
                isTabsLoading = false
            }
        }
    }

    fun filterTabData(query: String, tabIndex: Int) {
        when (tabIndex) {
            0 -> filteredCategories = if (query.isBlank()) allCategories
            else allCategories.filter {
                it.strCategory.contains(query, ignoreCase = true)
            }
            1 -> filteredCountries = if (query.isBlank()) allCountries
            else allCountries.filter {
                it.strArea.contains(query, ignoreCase = true)
            }
            2 -> filteredIngredients = if (query.isBlank()) allIngredients
            else allIngredients.filter {
                it.strIngredient.contains(query, ignoreCase = true)
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        searchJob?.cancel()
        if (query.isBlank()) {
            searchResults = emptyList()
            return
        }
        searchJob = viewModelScope.launch {
            delay(500L)
            isLoading = true
            errorMessage = null
            try {
                val response = if (query.length == 1)
                    remoteDataSource.searchMealByLetter(query)
                else
                    remoteDataSource.searchMealByName(query)
                searchResults = response.body()?.meals ?: emptyList()
                if (searchResults.isEmpty()) errorMessage = "No meals found!"
            } catch (e: Exception) {
                errorMessage = "Something went wrong!"
            } finally {
                isLoading = false
            }
        }
    }
}