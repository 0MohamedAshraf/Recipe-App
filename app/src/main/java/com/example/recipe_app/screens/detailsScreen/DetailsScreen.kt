package com.example.recipe_app.screens.detailsScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.recipe_app.components.CustomLoadingIndicator
import com.example.recipe_app.components.SectionHeader
import com.example.recipe_app.screens.detailsScreen.components.IngredientsGrid
import com.example.recipe_app.screens.detailsScreen.components.InstructionCard
import com.example.recipe_app.screens.detailsScreen.components.MealImage
import com.example.recipe_app.screens.detailsScreen.components.YoutubeScreen
import com.example.recipe_app.screens.detailsScreen.dto.RecipeIngredient
import com.example.recipe_app.screens.detailsScreen.dto.getIngredients
import com.example.recipe_app.screens.detailsScreen.viewmodel.MealDetailsViewModel
import com.example.recipe_app.screens.homeScreen.dto.Meal
import com.example.recipe_app.ui.theme.OrangeVariant

@Composable
fun DetailsScreen(
    detailsViewModel: MealDetailsViewModel,
    modifier: Modifier = Modifier
) {
    val meal by detailsViewModel.meal.collectAsStateWithLifecycle()
    val ingredients = meal?.getIngredients()


    DetailsContent(
        meal = meal,
        ingredients = ingredients,
        modifier = modifier
    )

}
@Composable
fun DetailsContent(
    meal: Meal?,
    ingredients: List<RecipeIngredient>?,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        MealImage(
            image = meal?.strMealThumb ?: "",
            mealName = meal?.strMeal ?: "",
            area = meal?.strArea ?: "",
            category = meal?.strCategory ?: "",
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            if (ingredients != null) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        SectionHeader("Ingredients")
                        Text(

                            text = "${ingredients.size} items",
                            color = OrangeVariant
                        )
                    }

                 IngredientsGrid(ingredients)
            }


            SectionHeader("Video Tutorial")


            if (meal?.strYoutube != null)
                YoutubeScreen(meal.strYoutube)


            SectionHeader("Preparation")

            InstructionCard(
                instructions = meal?.strInstructions ?: ""
            )
        }




    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailsScreenPreview(){

//    Scaffold {
//        DetailsContent(
//            meal = previewMeal,
//            ingredients = null,
//            modifier = Modifier.padding(it))
//    }
}