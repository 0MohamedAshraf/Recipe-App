package com.example.recipe_app.screens.homeScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipe_app.screens.homeScreen.viewmodel.MealViewModel
import com.example.recipe_app.screens.homeScreen.dto.Category

@Composable
fun CategoriesRow(
    categories: List<Category>,
    selectedCategoryIndex: Int,
    onCategorySelect: (Int, String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier,
        horizontalArrangement = Arrangement.spacedBy(24.dp)) {
        itemsIndexed(items = categories){ index,category ->
            CategoryItem(
                category.strCategory,
                category.strCategoryThumb,
                selected = selectedCategoryIndex == index,
                onItemClick = {
                    onCategorySelect(index, category.strCategory)
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CategoriesRowPreview(){
//    CategoriesRow()
}