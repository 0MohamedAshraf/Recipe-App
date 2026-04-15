package com.example.recipe_app.screens.detailsScreen.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipe_app.R
import com.example.recipe_app.screens.detailsScreen.dto.RecipeIngredient

@Composable
fun IngredientsGrid(list: List<RecipeIngredient>, modifier: Modifier = Modifier){

    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
    ) {
        items(list){ ingredient ->
            IngredientCard(
                image = ingredient.image,
                name = ingredient.name,
                measure = ingredient.measure

            )

        }
    }
}


@Preview(showBackground = true)
@Composable
fun IngredientsGridPreview(){

//    Scaffold {
//        IngredientsGrid(Modifier.padding(it))
//    }


}