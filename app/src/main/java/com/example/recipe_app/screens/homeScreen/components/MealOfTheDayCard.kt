package com.example.recipe_app.screens.homeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import com.example.recipe_app.ui.theme.OrangeVariant

@Composable
fun MealOfTheDayCard(title: String, image: String?, tags: String?, modifier: Modifier = Modifier){
    Card(modifier.fillMaxWidth().height(216.dp)) {
        Box(modifier = Modifier.fillMaxSize()){
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
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp).width(200.dp)
            ) {
                Text(
                    text = "Chief's Choice",
                    Modifier
                        .background(color = OrangeVariant, shape = RoundedCornerShape(16.dp))
                        .padding(4.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 24.sp

                )
                Spacer(modifier = Modifier.height(4.dp))
                if (tags != null) {
                    Text(
                        text = tags,
                        color = Color.LightGray
                    )
                }

            }
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = OrangeVariant),
                modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
            ) {
                Text(text = "View Recipe")
            }

        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MealOfTheDayCardPreview(){
//    MealOfTheDayCard(
//        title = "Lemon Herb Roasted Chicken",
//        image = painterResource(R.drawable.roasted_chicken),
//        tags = listOf("Chicken","Lunch")
//    )
}