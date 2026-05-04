package com.example.recipe_app.screens.splashScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.recipe_app.screens.splashScreen.viewmodel.SplashState
import com.example.recipe_app.screens.splashScreen.viewmodel.SplashViewModel

@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel,
    modifier: Modifier = Modifier,
    navigateToMain: () -> Unit,
    navigateToAuth: () -> Unit
) {
    val splashState by splashViewModel.splashState.collectAsStateWithLifecycle()

    var isAnimationFinished by remember { mutableStateOf(false) }

    SplashScreenAnimation(
        modifier = modifier,
        onAnimationEnd = {
            isAnimationFinished = true
        }
    )

    LaunchedEffect(key1 = isAnimationFinished, key2 = splashState) {
        if (isAnimationFinished) {
            when (splashState) {
                SplashState.TO_MAIN -> navigateToMain()
                SplashState.TO_AUTH -> navigateToAuth()
                SplashState.LOADING -> {

                }
            }
        }
    }
}