package com.example.recipe_app.screens.detailsScreen.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.recipe_app.R

@Composable
fun IngredientCard(
    image: String?,
    name: String,
    measure: String,
    modifier: Modifier = Modifier
){
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier.size(width = 200.dp, height = 68.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
            Spacer(Modifier.width(8.dp))
            Column(Modifier
                .fillMaxHeight()
                ) {
                Text(
                    text = name,
                    fontWeight = FontWeight.Black,
                    fontSize = 14.sp,
                    lineHeight = 12.sp,
                    maxLines = 2
                )
                Text(
                    text = measure,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    maxLines = 1
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IngredientCardPreview(){

//    IngredientCard(
//        image = R.drawable.temp,
//        name = "Rice Noodles Noodles",
//        measure = "140g"
//    )

}