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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.recipe_app.screens.homeScreen.viewmodel.MealViewModel
import com.example.recipe_app.NavBarItems
import com.example.recipe_app.R
import com.example.recipe_app.components.CategoriesRow
import com.example.recipe_app.components.MealLazyColumn
import com.example.recipe_app.components.MealOfTheDayCard
import com.example.recipe_app.components.SectionHeader
import com.example.recipe_app.network.ApiClient
import com.example.recipe_app.network.RemoteDataSourceImpl
import com.example.recipe_app.screens.homeScreen.repo.MealRepositoryImpl
import com.example.recipe_app.ui.theme.OrangeVariant
import com.example.recipe_app.ui.theme.Recipe_AppTheme


@Composable
fun HomeScreen(mealViewModel: MealViewModel,modifier: Modifier = Modifier){
    val randomMeal by mealViewModel.randomMeal.collectAsStateWithLifecycle()
    val categories by mealViewModel.categories.collectAsStateWithLifecycle()
    val meals by mealViewModel.categoryOfMeals.collectAsStateWithLifecycle()
    Column(modifier.padding(horizontal = 8.dp)) {
        Spacer(Modifier.height(8.dp))
        Row() {
            Icon(
                painter = painterResource(R.drawable.recommend),
                contentDescription = null,
                tint = OrangeVariant,
                modifier = Modifier.size(16.dp).align(Alignment.CenterVertically))
            SectionHeader(
                title = "Meal of the Day",
                modifier = Modifier.padding(start = 8.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))

        MealOfTheDayCard(
            title = randomMeal?.strMeal ?: "Error",
            image = randomMeal?.strMealThumb,
            tags = randomMeal?.strTags
        )
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
            SectionHeader(title = "Category")
            Text(
                text = "See All",
                color = OrangeVariant,
                textAlign = TextAlign.End
                )
        }
        Spacer(Modifier.height(16.dp))
        CategoriesRow(
            mealViewModel = mealViewModel,
            categories = categories
        )
        Spacer(Modifier.height(16.dp))

        SectionHeader(title = "Trending Meals")
        Spacer(Modifier.height(16.dp))
        MealLazyColumn(meals)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun HomeScreenPreview(){
    Recipe_AppTheme {
            var selectedIndex by rememberSaveable { mutableStateOf(0) }
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = { TopAppBar(title = { Text("RecipeHome", fontWeight = FontWeight.Bold) }) },
                bottomBar = {
                    NavigationBar {
                        NavBarItems.forEachIndexed { index, item ->
                            NavigationBarItem(
                                selected = selectedIndex == index,
                                icon = {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = null,
                                        tint =
                                            if(selectedIndex == index) OrangeVariant
                                            else LocalContentColor.current
                                    )
                                },
                                label = {Text(item.label)},
                                onClick = {
                                    selectedIndex = index
                                    item.icon.tintColor
                                }
                            )
                        }
                    }
                }
            ) { innerPadding ->
            HomeScreen(MealViewModel(MealRepositoryImpl(RemoteDataSourceImpl(ApiClient.service))),modifier = Modifier.fillMaxSize().padding(innerPadding))
        }
    }
}
