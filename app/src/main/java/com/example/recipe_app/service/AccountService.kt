package com.example.recipe_app.service

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow


interface AccountService {
    val currentUser : Flow<FirebaseUser?>
    suspend fun signInWithEmail(email: String, password: String)
    suspend fun signInWithGoogle(idToken: String)
    suspend fun signUp(email: String, password: String)
    suspend fun signOut()
    suspend fun deleteAccount()
    suspend fun continueAsGuest()
    fun hasUser() : Boolean
    fun getUserName() : String

     fun isEmailVerified(): Boolean

}