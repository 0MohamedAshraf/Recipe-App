package com.example.recipe_app.screens.splashScreen.viewmodel

import androidx.lifecycle.ViewModel
import com.example.recipe_app.service.AccountService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class SplashState {
    LOADING,
    TO_MAIN,
    TO_AUTH
}

class SplashViewModel(
    private val accountService: AccountService
) : ViewModel() {

    private val _splashState = MutableStateFlow(SplashState.LOADING)
    val splashState = _splashState.asStateFlow()

    init {
        checkSession()
    }

    private fun checkSession() {
        if (accountService.hasUser()) {
            _splashState.value = SplashState.TO_MAIN
        } else {
            _splashState.value = SplashState.TO_AUTH
        }

    }
}