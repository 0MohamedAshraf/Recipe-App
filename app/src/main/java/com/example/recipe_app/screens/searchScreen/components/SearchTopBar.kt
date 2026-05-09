package com.example.recipe_app.screens.searchScreen.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.recipe_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
){

    TopAppBar(
        title = {

            Text(
                text = stringResource(R.string.search_recipes),
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


@Preview(showBackground = true)
@Composable
fun SearchTopBarPreview(){
    SearchTopBar({})
}