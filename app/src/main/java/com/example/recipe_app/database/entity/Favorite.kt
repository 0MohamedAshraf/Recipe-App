package com.example.recipe_app.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorites",
    primaryKeys = ["id","userId"]
)
data class Favorite(
    val userId: String,
    val id: String,
    val name: String,
    val image: String,
    val category: String,
    val area: String


)