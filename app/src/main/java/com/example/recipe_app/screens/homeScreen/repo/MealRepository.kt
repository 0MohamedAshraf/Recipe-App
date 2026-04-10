package com.example.recipe_app.screens.homeScreen.repo

import com.example.recipe_app.screens.homeScreen.dto.CategoriesResponse
import com.example.recipe_app.screens.homeScreen.dto.MealResponse
import retrofit2.Response

interface MealRepository {

    suspend fun getRandomMeal(): Response<MealResponse>

    suspend fun getAllCategories(): Response<CategoriesResponse>

    suspend fun filterByCategory(category: String) : Response<MealResponse>
}