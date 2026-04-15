package com.example.recipe_app.screens.detailsScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.recipe_app.components.SectionHeader
import com.example.recipe_app.screens.detailsScreen.components.IngredientsGrid
import com.example.recipe_app.screens.detailsScreen.components.MealImage
import com.example.recipe_app.screens.detailsScreen.components.YoutubeScreen
import com.example.recipe_app.screens.detailsScreen.dto.getIngredients
import com.example.recipe_app.screens.detailsScreen.viewmodel.MealDetailsViewModel
import com.example.recipe_app.ui.theme.OrangeVariant

@Composable
fun DetailsScreen(
    detailsViewModel: MealDetailsViewModel,
    modifier: Modifier = Modifier
) {
    val meal by detailsViewModel.meal.collectAsStateWithLifecycle()
    val ingredients = meal?.getIngredients()
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxSize()
    ) {
        item {
            MealImage(
                image = meal?.strMealThumb ?:"",
                mealName = meal?.strMeal ?:"",
                area = meal?.strArea ?:"",
                category = meal?.strCategory ?:"",
            )
        }
        if(ingredients != null){
        item{
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                SectionHeader("Ingredients")
                Text(
                    text = "${ingredients.size.toString()} items",
                    color = OrangeVariant
                )
            }
        }
        item {  IngredientsGrid(ingredients) }
        }

        item {
            SectionHeader("Video Tutorial", Modifier.padding(start = 8.dp))
            YoutubeScreen("M7lc1UVf-VE")
        }

    }



}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailsScreenPreview(){
//    Scaffold {
//        DetailsScreen("",modifier = Modifier.padding(it))
//    }
}