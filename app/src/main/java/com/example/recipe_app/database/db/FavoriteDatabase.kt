package com.example.recipe_app.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipe_app.database.entity.Favorite
import com.example.recipe_app.database.dao.FavoriteDao

@Database(entities = [Favorite::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun favoriteDao() : FavoriteDao

    companion object{
        @Volatile
        private var INSTANCE : FavoriteDatabase? = null

        fun getInstance(context: Context) : FavoriteDatabase{
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDatabase::class.java, "favorites_database"
                )
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}