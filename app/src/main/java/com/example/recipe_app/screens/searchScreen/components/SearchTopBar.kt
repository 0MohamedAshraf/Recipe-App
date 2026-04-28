package com.example.recipe_app.screens.searchScreen.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    onBackClick: () -> Unit,
    onMicClick: () -> Unit,
    modifier: Modifier = Modifier
){

    TopAppBar(
        title = {

            Text(
                text = "Search Recipes",
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
        actions = {
            IconButton(onClick = onMicClick) {
                Icon(
                    imageVector =Icons.Default.Mic,
                    contentDescription = null,

                )
            }
        }
    )

}


@Preview(showBackground = true)
@Composable
fun SearchTopBarPreview(){
    SearchTopBar({},{})
}