package com.example.recipe_app.screens.searchScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.recipe_app.R

@Composable
fun SearchScreen(
    onNavigateToSearchResult: () -> Unit,
    onNavigateToMealsList: (filterType: String, filterValue: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp))
                .background(Color(0xFFFFF3E6))
                .clickable { onNavigateToSearchResult() }
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color(0xFFFF8C42)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Pasta, Avocado33, Italian...", color = Color.Gray, fontSize = 15.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Categories", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(
                "See all",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFFFF8C42),
                modifier = Modifier.clickable { onNavigateToSearchResult() }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(Modifier.fillMaxWidth()) {
            CategoryItem("Breakfast", "https://www.themealdb.com/images/category/breakfast.png", Modifier.weight(1f)) {
                onNavigateToMealsList("category", "Breakfast")
            }
            Spacer(modifier = Modifier.width(8.dp))
            CategoryItem("Pasta", "https://www.themealdb.com/images/category/pasta.png", Modifier.weight(1f)) {
                onNavigateToMealsList("category", "Pasta")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth()) {
            CategoryItem("Seafood", "https://www.themealdb.com/images/category/seafood.png", Modifier.weight(1f)) {
                onNavigateToMealsList("category", "Seafood")
            }

            Spacer(modifier = Modifier.width(8.dp))
            CategoryItem("Dessert", "https://www.themealdb.com/images/category/dessert.png", Modifier.weight(1f)) {
                onNavigateToMealsList("category", "Dessert")
            }
        }


        Spacer(modifier = Modifier.height(16.dp))
        Text("Cuisines", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(12.dp))

        Row(Modifier.fillMaxWidth()) {
            CuisineItem("Italian", R.drawable.italian, Modifier.weight(1f)) {
                onNavigateToMealsList("country", "Italian")
            }
            CuisineItem("Mexican", R.drawable.mexican, Modifier.weight(1f)) {
                onNavigateToMealsList("country", "Mexican")
            }
            CuisineItem("Japanese", R.drawable.japanese, Modifier.weight(1f)) {
                onNavigateToMealsList("country", "Japanese")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth()) {
            CuisineItem("Egyptian", R.drawable.kushari, Modifier.weight(1f)) {
                onNavigateToMealsList("country", "Egyptian")
            }
            CuisineItem("Algerian", R.drawable.algeria, Modifier.weight(1f)) {
                onNavigateToMealsList("country", "Algerian")
            }
            CuisineItem("Greek", R.drawable.greek, Modifier.weight(1f)) {
                onNavigateToMealsList("country", "Greek")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Popular Ingredients", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(12.dp))

        Row(Modifier.fillMaxWidth()) {
            IngredientItem("Avocado", R.drawable.avocado, Modifier.weight(1f)) {
                onNavigateToMealsList("ingredient", "Avocado")
            }
            IngredientItem("Chicken", R.drawable.chicken, Modifier.weight(1f)) {
                onNavigateToMealsList("ingredient", "Chicken")
            }
            IngredientItem("Tomato", R.drawable.tomato, Modifier.weight(1f)) {
                onNavigateToMealsList("ingredient", "Tomato")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth()) {
            IngredientItem("Beef", R.drawable.beef, Modifier.weight(1f)) {
                onNavigateToMealsList("ingredient", "Beef")
            }
            IngredientItem("Pork", R.drawable.pork, Modifier.weight(1f)) {
                onNavigateToMealsList("ingredient", "Pork")
            }
            IngredientItem("Salmon", R.drawable.salmon, Modifier.weight(1f)) {
                onNavigateToMealsList("ingredient", "Salmon")
            }
        }

    }
}
@Composable
fun CategoryItem(title: String, image: String, modifier: Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .height(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = image,
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
                    )
                )
        )
        Text(
            text = title,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp)
        )
    }
}

@Composable
fun CuisineItem(title: String, image: Int, modifier: Modifier, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable { onClick() }
    ) {
        AsyncImage(
            model = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .border(3.dp, Color(0xFFFFE0CC), CircleShape)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun IngredientItem(title: String, image: Int, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier
            .padding(6.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFFFF3E6))
            .border(1.dp, Color(0xFFFFE0CC), RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = title,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(title, fontSize = 12.sp, color = Color.DarkGray)
    }
}