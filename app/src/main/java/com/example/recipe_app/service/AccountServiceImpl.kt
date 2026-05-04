package com.example.recipe_app.service

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AccountServiceImpl : AccountService {

    override val currentUser : Flow<FirebaseUser?>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { trySend(it.currentUser) }
            Firebase.auth.addAuthStateListener(listener)
            awaitClose { Firebase.auth.removeAuthStateListener(listener) }
        }

    override val currentUserId : String
        get() = Firebase.auth.currentUser?.uid.orEmpty()

    override suspend fun signInWithEmail(email: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(email,password).await()
    }


    override suspend fun signInWithGoogle(idToken: String) {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        Firebase.auth.signInWithCredential(firebaseCredential).await()
    }

    override suspend fun signUp(email: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(email,password).await()
    }

    override  fun signOut() {
        Firebase.auth.signOut()
    }

    override suspend fun deleteAccount() {
        Firebase.auth.currentUser?.delete()?.await()
    }

    override suspend fun continueAsGuest() {
        Firebase.auth.signInAnonymously().await()
    }

    override fun hasUser(): Boolean {
        return Firebase.auth.currentUser != null
    }

    override fun getUserName(): String {
        val name = Firebase.auth.currentUser?.displayName
        return if(name.isNullOrEmpty()) "Guest" else name
    }

    override fun isEmailVerified(): Boolean {
        return Firebase.auth.currentUser?.isEmailVerified == true
    }

    override fun getJoinDate(): String {

        val creationTimestamp = Firebase.auth.currentUser?.metadata?.creationTimestamp

        return if (creationTimestamp != null) {
            val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            "Joined ${sdf.format(Date(creationTimestamp))}"
        } else {
            "Joined recently"
        }
    }

    override fun isGuest(): Boolean {
        return Firebase.auth.currentUser?.isAnonymous == true
    }
}