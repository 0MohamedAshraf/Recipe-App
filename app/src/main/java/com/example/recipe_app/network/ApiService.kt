package com.example.recipe_app.network

import com.example.recipe_app.screens.homeScreen.dto.CategoriesResponse
import com.example.recipe_app.screens.homeScreen.dto.MealResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("random.php")
    suspend fun getRandomMeal() : Response<MealResponse>
    @GET("categories.php")
    suspend fun getAllCategories() : Response<CategoriesResponse>

    @GET("filter.php")
    suspend fun filterByCategory(@Query("c") category: String) : Response<MealResponse>
}