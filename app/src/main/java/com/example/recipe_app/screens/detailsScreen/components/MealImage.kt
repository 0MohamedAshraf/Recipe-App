package com.example.recipe_app.screens.detailsScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.recipe_app.R
import com.example.recipe_app.ui.theme.OrangeVariant

@Composable
fun MealImage(image: String,mealName: String, area: String, category: String, modifier: Modifier = Modifier){

    Box(modifier.fillMaxWidth().height(350.dp)){
        AsyncImage(
            model = image,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.8f)
                        ),
                        startY = 150f
                    )
                )
        )
        Column(
            modifier = Modifier
                .padding(16.dp)
                .height(140.dp)
                .align(Alignment.BottomStart),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = category.uppercase(),
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .background(color = OrangeVariant, shape = RoundedCornerShape(16.dp))
                        .padding(8.dp)
                )
                Text(
                    text = area.uppercase(),
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .background(color = Color.White.copy(0.2f), shape = RoundedCornerShape(16.dp),)
                        .padding(8.dp)

                )
            }
            Text(
                text = mealName.uppercase(),
                fontSize = 32.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                lineHeight = 38.sp
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = false)
@Composable
fun MealImagePreview(){

    Scaffold() {
        MealImage(
            R.drawable.temp.toString(),
            "Bang bang prawn salad",
            "Vietnamese","Seafood",
            modifier = Modifier.padding(it))
    }
}