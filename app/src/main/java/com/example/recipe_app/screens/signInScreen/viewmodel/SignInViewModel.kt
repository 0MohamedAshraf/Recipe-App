package com.example.recipe_app.screens.signInScreen.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.service.AccountService
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewModel(
    private val accountService: AccountService
) : ViewModel() {

    private val _email = MutableStateFlow("")
     val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
     val password = _password.asStateFlow()

    val isLoading = MutableStateFlow(false)

    fun updateEmail(email: String){
        _email.value = email
    }
    fun updatePassword(password: String){
        _password.value = password
    }


    fun authUserEmail(onSignIn: () -> Unit, onError: (String) -> Unit){
        if(_email.value.isEmpty() || _password.value.isEmpty()) return

        viewModelScope.launch {
            isLoading.value = true
            try {
                accountService.signInWithEmail(_email.value, _password.value)

                if (accountService.isEmailVerified()) {
                    isLoading.value = false
                    onSignIn()
                } else {
                    accountService.signOut()
                    isLoading.value = false
                    onError("Please verify your email address to sign in.")
                }
            } catch (e: Exception) {
                isLoading.value = false
                onError(e.localizedMessage ?: "Authentication failed")
            }
        }
    }

    fun authWithGoogle(idToken: String, onSignIn: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                accountService.signInWithGoogle(idToken)
                isLoading.value = false

                onSignIn()
            } catch (e: Exception) {
                isLoading.value = false
                onError(e.localizedMessage ?: "Google Sign-In failed")
            }
        }
    }
    fun continueAsGuest(onSignIn: () -> Unit){
        viewModelScope.launch {
            try {
                accountService.continueAsGuest()
                onSignIn()
            }catch (e: Exception){
                Log.d("AUTH -> ", "authUser: Can't Authenticate as Guest")
            }
        }
    }
}