package com.example.recipe_app

import kotlinx.serialization.Serializable

@Serializable
object AuthGraph

@Serializable
object MainGraph

sealed interface Routes {

    @Serializable
    data object Splash : Routes

    @Serializable
    data class Details(val id : String)
    @Serializable
    data object SignIn : Routes

    @Serializable
    data object SignUp : Routes

    @Serializable
    data object Home : Routes

    @Serializable
    data class Profile(val userId: String)

    @Serializable
    data object Search
    @Serializable
    data object Favorites
    @Serializable
    data object SearchResult : Routes

    @Serializable
    data class MealsList(
        val filterType: String,
        val filterValue: String
    ) : Routes
}