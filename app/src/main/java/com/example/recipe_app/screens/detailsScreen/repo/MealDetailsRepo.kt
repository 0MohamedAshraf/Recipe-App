package com.example.recipe_app.screens.detailsScreen.repo

import com.example.recipe_app.screens.homeScreen.dto.MealResponse
import retrofit2.Response

interface MealDetailsRepo {

    suspend fun getMealById(id : String) : Response<MealResponse>
}