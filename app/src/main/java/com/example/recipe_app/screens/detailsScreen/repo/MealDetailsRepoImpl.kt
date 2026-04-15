package com.example.recipe_app.screens.detailsScreen.repo

import com.example.recipe_app.network.RemoteDataSource
import com.example.recipe_app.screens.homeScreen.dto.MealResponse
import retrofit2.Response

class MealDetailsRepoImpl(
    private val remoteDataSource: RemoteDataSource
) : MealDetailsRepo {
    override suspend fun getMealById(id: String): Response<MealResponse> {
        return remoteDataSource.getMealById(id)
    }
}