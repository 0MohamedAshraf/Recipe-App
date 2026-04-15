package com.example.recipe_app.screens.homeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.recipe_app.ui.theme.OrangeVariant

@Composable
fun CategoryItem(
    name: String,
    image: String?,
    selected: Boolean,
    onItemClick: ()-> Unit,
    modifier: Modifier = Modifier){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(if (selected) OrangeVariant else Color.White)
                .size(64.dp)
                .clickable(onClick = onItemClick),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(56.dp)

            )
        }
        Text(text = name)
    }
}


@Preview(showBackground = true)
@Composable
fun CategoryItemPreview(){
//    CategoryItem("Chicken","")
}