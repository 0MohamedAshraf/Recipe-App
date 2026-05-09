package com.example.recipe_app.screens.favScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.recipe_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteTopBar(
    onBackClick: () -> Unit
){
    TopAppBar(
        title = {

            Text(
                text = stringResource(R.string.my_favorites),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector =  Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                )
            }
        },

        )
}