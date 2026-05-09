package com.example.recipe_app.service

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AccountServiceImpl : AccountService {

    override val currentUser: Flow<FirebaseUser?>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                trySend(auth.currentUser)
            }
            Firebase.auth.addAuthStateListener(listener)
            awaitClose { Firebase.auth.removeAuthStateListener(listener) }
        }

    override val userProfile: Flow<UserProfile>
        get() = currentUser.map { user ->
            if (user == null) {
                UserProfile("Guest", "", null)
            } else {
                val name = if(!user.displayName.isNullOrEmpty()) user.displayName else "User"
                val date = user.metadata?.creationTimestamp?.let {
                    val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                    "Joined ${sdf.format(Date(it))}"
                } ?: "Joined recently"

                UserProfile(
                    name = name ?: "User",
                    joinDate = date,
                    photoUrl = user.photoUrl?.toString()
                )
            }
        }

    override val currentUserId: String
        get() = Firebase.auth.currentUser?.uid.orEmpty()

    override suspend fun signInWithEmail(email: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun signInWithGoogle(idToken: String) {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        Firebase.auth.signInWithCredential(firebaseCredential).await()
    }

    override suspend fun signUp(email: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(email, password).await()
    }

    override fun signOut() {
        Firebase.auth.signOut()
    }

    override suspend fun deleteAccount() {
        Firebase.auth.currentUser?.delete()?.await()
    }

    override suspend fun continueAsGuest() {
        Firebase.auth.signInAnonymously().await()
    }

    override suspend fun uploadProfileImage(uri: Uri): String? {
        return try {
            val uid = currentUserId
            if (uid.isBlank()) return null

            val storageRef = FirebaseStorage.getInstance()
                .reference
                .child("profile_images/$uid.jpg")

            storageRef.putFile(uri).await()

            val downloadUrl = storageRef.downloadUrl.await()

            val profileUpdates = UserProfileChangeRequest.Builder()
                .setPhotoUri(downloadUrl)
                .build()

            val user = Firebase.auth.currentUser
            user?.updateProfile(profileUpdates)?.await()
            user?.reload()?.await()

            downloadUrl.toString()
        } catch (e: Exception) {
            null
        }
    }

    override fun hasUser(): Boolean {
        return Firebase.auth.currentUser != null
    }

    override fun getUserName(): String {
        return Firebase.auth.currentUser?.displayName ?: "Guest"
    }

    override fun isEmailVerified(): Boolean {
        return Firebase.auth.currentUser?.isEmailVerified == true
    }

    override fun getJoinDate(): String {
        val timestamp = Firebase.auth.currentUser?.metadata?.creationTimestamp
        return if (timestamp != null) {
            val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            "Joined ${sdf.format(Date(timestamp))}"
        } else {
            "Joined recently"
        }
    }

    override fun isGuest(): Boolean {
        return Firebase.auth.currentUser?.isAnonymous == true
    }
}