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
    data object SignIn : Routes

    @Serializable
    data object SignUp : Routes

    @Serializable
    data object Home : Routes
}