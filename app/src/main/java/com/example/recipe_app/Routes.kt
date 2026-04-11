package com.example.recipe_app

import kotlinx.serialization.Serializable

@Serializable
object MainGraph
@Serializable
object AuthGraph

sealed interface Routes {

    @Serializable
    object Home

    @Serializable
    object Splash
    @Serializable
    object SignIn
    @Serializable
    object SignUp
}