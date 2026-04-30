package com.example.recipe_app.screens.splashScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.recipe_app.R

@Composable
fun SplashScreenAnimation(
    modifier: Modifier = Modifier,
    onAnimationEnd : () -> Unit
    ) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.boiling_pot)
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1
    )
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress }
        )
    }

    if (progress == 1.0f){
        onAnimationEnd()
    }
}