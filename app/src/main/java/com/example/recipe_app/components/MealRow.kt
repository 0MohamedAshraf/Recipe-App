package com.example.recipe_app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MealRow(
    name: String,
    image: String?,
    onMealRowClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .clickable(onClick = onMealRowClick)
        .size(width = 358.dp, height = 122.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                model = image,
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))

            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 4.dp, end = 16.dp)
                    .weight(1f)
            )
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = "Favorite Icon",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MealRowPreview(){
//    MealRow({})
}