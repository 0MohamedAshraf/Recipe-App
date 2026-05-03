package com.example.recipe_app.screens.homeScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipe_app.screens.homeScreen.dto.Meal

@Composable
fun MealLazyColumn(
    meals: List<Meal>,
    onMealClick : (String) -> Unit,
    modifier: Modifier = Modifier,
    favoriteIds: List<String>,
    onFavClick: (Meal) -> Unit
){

    LazyColumn(
        modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items = meals){ meal ->
            MealRow(
                name = meal.strMeal,
                image = meal.strMealThumb,
                onMealRowClick = {
                    onMealClick(meal.idMeal)
                },
                isFavorite = favoriteIds.contains(meal.idMeal),
                onFavClick = {
                    onFavClick(meal)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MealLazyColumnPreview(){
//    MealLazyColumn()
}