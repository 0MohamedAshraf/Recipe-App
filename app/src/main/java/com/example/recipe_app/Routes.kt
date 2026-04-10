package com.example.recipe_app

import kotlinx.serialization.Serializable

sealed interface Routes {

    @Serializable
    object Home

    @Serializable
    object Splash
}