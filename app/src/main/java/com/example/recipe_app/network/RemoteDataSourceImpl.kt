package com.example.recipe_app.network

import com.example.recipe_app.screens.homeScreen.dto.CategoriesResponse
import com.example.recipe_app.screens.homeScreen.dto.MealResponse
import com.example.recipe_app.screens.searchScreen.dto.CountryResponse
import com.example.recipe_app.screens.searchScreen.dto.IngredientResponse
import retrofit2.Response

class RemoteDataSourceImpl(
    private val apiService: ApiService
): RemoteDataSource {
    override suspend fun getRandomMeal(): Response<MealResponse> {
        return apiService.getRandomMeal()
    }

    override suspend fun getAllCategories(): Response<CategoriesResponse> {
        return apiService.getAllCategories()
    }

    override suspend fun filterByCategory(category: String): Response<MealResponse> {
        return apiService.filterByCategory(category)
    }

    override suspend fun getMealById(id: String): Response<MealResponse> {
        return apiService.getMealByID(id)
    }
    override suspend fun searchMealByName(name: String): Response<MealResponse> =
        apiService.searchMealByName(name)

    override suspend fun searchMealByLetter(letter: String): Response<MealResponse> =
        apiService.searchMealByLetter(letter)

    override suspend fun filterByCountry(country: String): Response<MealResponse> =
        apiService.filterByCountry(country)

    override suspend fun filterByIngredient(ingredient: String): Response<MealResponse> =
        apiService.filterByIngredient(ingredient)

    override suspend fun getAllCountries(): Response<CountryResponse> =
        apiService.getAllCountries()

    override suspend fun getAllIngredients(): Response<IngredientResponse> =
        apiService.getAllIngredients()
}