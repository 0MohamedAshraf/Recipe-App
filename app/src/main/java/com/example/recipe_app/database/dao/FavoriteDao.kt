package com.example.recipe_app.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.recipe_app.database.entity.Favorite

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorites WHERE userId = :userId")
    suspend fun getAll(userId: String) : List<Favorite>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE id = :mealId AND userId = :userId)")
    suspend fun isMealFavorited(mealId: String, userId: String): Boolean
    @Insert(onConflict = REPLACE)
    suspend fun addFavorite(vararg favorite: Favorite)

    @Query("DELETE FROM favorites WHERE userId = :userId AND id = :mealId")
    suspend fun removeFavorite(userId: String,mealId: String) : Int
}