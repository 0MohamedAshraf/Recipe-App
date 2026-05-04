package com.example.recipe_app.screens.searchScreen.repo

import com.example.recipe_app.screens.homeScreen.dto.CategoriesResponse
import com.example.recipe_app.screens.homeScreen.dto.MealResponse
import com.example.recipe_app.screens.searchScreen.dto.CountryResponse
import com.example.recipe_app.screens.searchScreen.dto.IngredientResponse
import retrofit2.Response

interface SearchRepository {

    suspend fun getAllCategories() : Response<CategoriesResponse>

    suspend fun getAllCountries(): Response<CountryResponse>

    suspend fun getAllIngredients(): Response<IngredientResponse>

    suspend fun searchMealByLetter(letter: String): Response<MealResponse>

    suspend fun searchMealByName(name: String): Response<MealResponse>

    suspend fun filterByCountry(country: String): Response<MealResponse>

    suspend fun filterByIngredient(ingredient: String): Response<MealResponse>

    suspend fun filterByCategory(category: String) : Response<MealResponse>




}