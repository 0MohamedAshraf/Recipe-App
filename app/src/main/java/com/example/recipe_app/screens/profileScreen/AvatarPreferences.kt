package com.example.recipe_app.screens.profileScreen

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "profile_prefs")

class AvatarPreferences(
    private val context: Context
) {

    companion object {
        private val SELECTED_AVATAR_KEY = intPreferencesKey("selected_avatar")
    }

    val selectedAvatar: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[SELECTED_AVATAR_KEY] ?: 0
    }

    suspend fun saveSelectedAvatar(index: Int) {
        context.dataStore.edit { preferences ->
            preferences[SELECTED_AVATAR_KEY] = index
        }
    }
}