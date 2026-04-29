package com.example.recipe_app.network

import com.example.recipe_app.screens.homeScreen.dto.CategoriesResponse
import com.example.recipe_app.screens.homeScreen.dto.MealResponse
import com.example.recipe_app.screens.searchScreen.dto.CountryResponse
import com.example.recipe_app.screens.searchScreen.dto.IngredientResponse
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
    @GET("lookup.php")
    suspend fun getMealByID(@Query("i") id : String) : Response<MealResponse>
    @GET("search.php")
    suspend fun searchMealByName(@Query("s") name: String): Response<MealResponse>
    @GET("search.php")
    suspend fun searchMealByLetter(@Query("f") letter: String): Response<MealResponse>
    @GET("filter.php")
    suspend fun filterByCountry(@Query("a") country: String): Response<MealResponse>
    @GET("filter.php")
    suspend fun filterByIngredient(@Query("i") ingredient: String): Response<MealResponse>
    @GET("list.php")
    suspend fun getAllCountries(@Query("a") list: String = "list"): Response<CountryResponse>
    @GET("list.php")
    suspend fun getAllIngredients(@Query("i") list: String = "list"): Response<IngredientResponse>

}