package com.example.recipe_app.screens.detailsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.recipe_app.R
import com.example.recipe_app.components.SectionHeader
import com.example.recipe_app.screens.detailsScreen.components.IngredientsGrid
import com.example.recipe_app.screens.detailsScreen.components.InstructionCard
import com.example.recipe_app.screens.detailsScreen.components.MealImage
import com.example.recipe_app.screens.detailsScreen.components.YoutubeScreen
import com.example.recipe_app.screens.detailsScreen.dto.RecipeIngredient
import com.example.recipe_app.screens.detailsScreen.dto.getIngredients
import com.example.recipe_app.screens.detailsScreen.viewmodel.MealDetailsViewModel
import com.example.recipe_app.screens.homeScreen.dto.Meal
import com.example.recipe_app.screens.noConnectionScreen.OfflineScreen

@Composable
fun DetailsScreen(
    detailsViewModel: MealDetailsViewModel,
    modifier: Modifier = Modifier
) {
    val isOffline by detailsViewModel.isOffline.collectAsStateWithLifecycle()
    val meal by detailsViewModel.meal.collectAsStateWithLifecycle()
    val ingredients = meal?.getIngredients()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (isOffline) {
            OfflineScreen(
                onTryAgain = {
                    detailsViewModel.getMealById(meal?.idMeal.orEmpty())
                }
            )
        } else {
            DetailsContent(
                meal = meal,
                ingredients = ingredients
            )
        }
    }
}

@Composable
fun DetailsContent(
    meal: Meal?,
    ingredients: List<RecipeIngredient>?,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxSize()
    ) {
        item {
            MealImage(
                image = meal?.strMealThumb ?: "",
                mealName = meal?.strMeal ?: "",
                area = meal?.strArea ?: "",
                category = meal?.strCategory ?: "",
            )
        }
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 24.dp)
            ) {
                if (ingredients != null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        SectionHeader(stringResource(R.string.ingredients))
                        Text(
                            text = "${ingredients.size} items",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    IngredientsGrid(ingredients)
                }

                SectionHeader(stringResource(R.string.video_tutorial))

                if (!meal?.strYoutube.isNullOrEmpty()) {
                    YoutubeScreen(meal!!.strYoutube!!)
                }

                SectionHeader(stringResource(R.string.preparation))

                InstructionCard(
                    instructions = meal?.strInstructions ?: ""
                )
            }
        }
    }
}
