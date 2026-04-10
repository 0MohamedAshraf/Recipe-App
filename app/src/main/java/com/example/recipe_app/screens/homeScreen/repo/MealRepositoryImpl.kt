package com.example.recipe_app.screens.homeScreen.repo

import com.example.recipe_app.network.RemoteDataSource
import com.example.recipe_app.screens.homeScreen.dto.CategoriesResponse
import com.example.recipe_app.screens.homeScreen.dto.MealResponse
import retrofit2.Response

class MealRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : MealRepository {
    override suspend fun getRandomMeal(): Response<MealResponse> {
        return remoteDataSource.getRandomMeal()
    }

    override suspend fun getAllCategories(): Response<CategoriesResponse> {
        return remoteDataSource.getAllCategories()
    }

    override suspend fun filterByCategory(category: String): Response<MealResponse> {
        return remoteDataSource.filterByCategory(category)
    }

}