package com.example.recipe_app.repo

import com.example.recipe_app.dataSource.network.RemoteDataSource
import com.example.recipe_app.dataSource.network.dto.CategoriesResponse
import com.example.recipe_app.dataSource.network.dto.MealResponse
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