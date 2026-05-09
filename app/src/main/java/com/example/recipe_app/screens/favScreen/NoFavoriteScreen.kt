package com.example.recipe_app.screens.favScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipe_app.R

@Composable
fun NoFavoriteScreen(modifier: Modifier = Modifier){
    Column(
        modifier =  modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.HeartBroken,
            contentDescription = "No Favorite",
            tint = Color.Red,
            modifier = Modifier.size(250.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.no_items_in_favorite),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Text(
            text = stringResource(R.string.add_recipes_to_your_favorites_to_see_them_here),
            color = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NoFavoriteScreenPreview(){
    NoFavoriteScreen()
}