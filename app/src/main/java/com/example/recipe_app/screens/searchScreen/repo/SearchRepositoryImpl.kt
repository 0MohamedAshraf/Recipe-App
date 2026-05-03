package com.example.recipe_app.screens.searchScreen.repo

import com.example.recipe_app.network.ApiService
import com.example.recipe_app.network.RemoteDataSource
import com.example.recipe_app.screens.homeScreen.dto.CategoriesResponse
import com.example.recipe_app.screens.homeScreen.dto.MealResponse
import com.example.recipe_app.screens.searchScreen.dto.CountryResponse
import com.example.recipe_app.screens.searchScreen.dto.IngredientResponse
import retrofit2.Response

class SearchRepositoryImpl(
    private val remoteDataSource : RemoteDataSource
) : SearchRepository {
    override suspend fun getAllCategories(): Response<CategoriesResponse> {
        return remoteDataSource.getAllCategories()
    }

    override suspend fun getAllCountries(): Response<CountryResponse> {
        return remoteDataSource.getAllCountries()
    }

    override suspend fun getAllIngredients(): Response<IngredientResponse> {
        return remoteDataSource.getAllIngredients()
    }

    override suspend fun searchMealByLetter(letter: String): Response<MealResponse> {
        return remoteDataSource.searchMealByLetter(letter)
    }

    override suspend fun searchMealByName(name: String): Response<MealResponse> {
        return remoteDataSource.searchMealByName(name)
    }

    override suspend fun filterByCountry(country: String): Response<MealResponse> {
        return remoteDataSource.filterByCountry(country)
    }

    override suspend fun filterByIngredient(ingredient: String): Response<MealResponse> {
        return remoteDataSource.filterByIngredient(ingredient)
    }

    override suspend fun filterByCategory(category: String): Response<MealResponse> {
        return remoteDataSource.filterByCategory(category)
    }
}