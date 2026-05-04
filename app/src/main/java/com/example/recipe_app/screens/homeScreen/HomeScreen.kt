package com.example.recipe_app.screens.homeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.recipe_app.R
import com.example.recipe_app.screens.homeScreen.components.CategoriesRow
import com.example.recipe_app.screens.homeScreen.components.MealLazyColumn
import com.example.recipe_app.screens.homeScreen.components.MealOfTheDayCard
import com.example.recipe_app.components.SectionHeader
import com.example.recipe_app.database.dao.FavoriteDao
import com.example.recipe_app.database.entity.Favorite
import com.example.recipe_app.navBarItems
import com.example.recipe_app.screens.homeScreen.dto.Category
import com.example.recipe_app.screens.homeScreen.dto.Meal
import com.example.recipe_app.screens.homeScreen.viewmodel.MealViewModel
import com.example.recipe_app.ui.theme.OrangeVariant
import com.example.recipe_app.ui.theme.Recipe_AppTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun HomeScreen(
    mealViewModel: MealViewModel,
    onMealClick : (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val randomMeal by mealViewModel.randomMeal.collectAsStateWithLifecycle()
    val categories by mealViewModel.categories.collectAsStateWithLifecycle()
    val meals by mealViewModel.categoryOfMeals.collectAsStateWithLifecycle()
    val favoriteIds by mealViewModel.favIds.collectAsStateWithLifecycle()
    val selectedCategoryIndex by mealViewModel.selectedCategoryIndex.collectAsStateWithLifecycle()

    HomeScreenContent(
        randomMeal = randomMeal,
        categories = categories,
        meals = meals,
        favoriteIds = favoriteIds,
        onMealClick = onMealClick,
        selectedCategoryIndex = selectedCategoryIndex,
        onCategorySelect = { newIndex, categoryName ->
            mealViewModel.setSelectedCategoryIndex(newIndex)
            mealViewModel.getMealByCategory(categoryName)
        },
        modifier = modifier,
        onFavClick = { meal ->
            val fav = Favorite(
                userId = Firebase.auth.currentUser?.uid.orEmpty(),
                id = meal.idMeal,
                name = meal.strMeal,
                image = meal.strMealThumb.orEmpty(),
                category = meal.strCategory.orEmpty(),
                area = meal.strArea.orEmpty()
            )
            if(favoriteIds.contains(meal.idMeal)){
                mealViewModel.removeFromFav(meal.idMeal)
            }
            else
                mealViewModel.addToFavorite(fav)


        }
    )
}


@Composable
fun HomeScreenContent(
    randomMeal: Meal?,
    categories: List<Category>,
    meals: List<Meal>,
    selectedCategoryIndex: Int,
    onMealClick: (String) -> Unit,
    onCategorySelect: (Int,String) -> Unit,
    onFavClick: (Meal) -> Unit,
    favoriteIds: List<String>,
    modifier: Modifier = Modifier
) {



    Column(
        modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(8.dp))

        Row {
            Icon(
                painter = painterResource(R.drawable.recommend),
                contentDescription = null,
                tint = OrangeVariant,
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.CenterVertically)
            )

            SectionHeader(
                title = "Meal of the Day",
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        MealOfTheDayCard(
            title = randomMeal?.strMeal ?: "",
            image = randomMeal?.strMealThumb,
            tags = randomMeal?.strTags,
            onViewRecipeClick = { onMealClick(randomMeal?.idMeal ?: "") }
        )

        Spacer(Modifier.height(16.dp))

        SectionHeader(title = "Category")

        Spacer(Modifier.height(16.dp))

        CategoriesRow(
            categories = categories,
            selectedCategoryIndex = selectedCategoryIndex,
            onCategorySelect = { newIndex, categoryName ->
                onCategorySelect(newIndex, categoryName)
            }
        )

        Spacer(Modifier.height(16.dp))

        SectionHeader(title = "Trending Meals")
        Spacer(Modifier.height(16.dp))

        MealLazyColumn(
            meals = meals,
            onMealClick = onMealClick,
            onFavClick = onFavClick,
            favoriteIds = favoriteIds ,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {

}