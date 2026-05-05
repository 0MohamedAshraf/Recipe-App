package com.example.recipe_app.service

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

data class UserProfile(
    val name: String,
    val joinDate: String,
    val photoUrl: String? = null
)

interface AccountService {
    val currentUser: Flow<FirebaseUser?>
    val userProfile: Flow<UserProfile>
    val currentUserId: String

    suspend fun signInWithEmail(email: String, password: String)
    suspend fun signInWithGoogle(idToken: String)
    suspend fun signUp(email: String, password: String)
    fun signOut()
    suspend fun deleteAccount()
    suspend fun continueAsGuest()

    suspend fun uploadProfileImage(uri: Uri): String?

    fun hasUser(): Boolean
    fun getUserName(): String
    fun isEmailVerified(): Boolean
    fun getJoinDate(): String
    fun isGuest(): Boolean
}