package com.example.recipe_app.screens.searchScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.recipe_app.Routes
import com.example.recipe_app.screens.searchScreen.dto.getFlagEmoji
import com.example.recipe_app.screens.searchScreen.viewmodel.SearchViewModel

@Composable
fun SearchResultScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SearchViewModel = viewModel()
) {
    var searchValue by remember { mutableStateOf("") }
    var showCategories by remember { mutableStateOf(false) }
    var showCountries by remember { mutableStateOf(false) }
    var showIngredients by remember { mutableStateOf(false) }

    LaunchedEffect(searchValue, showCategories, showCountries, showIngredients) {
        if (showCategories) viewModel.filterTabData(searchValue, 0)
        if (showCountries) viewModel.filterTabData(searchValue, 1)
        if (showIngredients) viewModel.filterTabData(searchValue, 2)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = searchValue,
            onValueChange = { searchValue = it },
            placeholder = { Text("Search...") },
            shape = RoundedCornerShape(28.dp),
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null, tint = Color(0xFFFF8C42))
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFFFF3E6),
                unfocusedContainerColor = Color(0xFFFFF3E6),
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = showCategories,
                onClick = {
                    showCategories = !showCategories
                    if (showCategories) viewModel.filterTabData(searchValue, 0)
                },
                label = { Text("Category") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFFFF8C42),
                    selectedLabelColor = Color.White
                )
            )
            FilterChip(
                selected = showIngredients,
                onClick = {
                    showIngredients = !showIngredients
                    if (showIngredients) viewModel.filterTabData(searchValue, 2)
                },
                label = { Text("Ingredient") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFFFF8C42),
                    selectedLabelColor = Color.White
                )
            )
            FilterChip(
                selected = showCountries,
                onClick = {
                    showCountries = !showCountries
                    if (showCountries) viewModel.filterTabData(searchValue, 1)
                },
                label = { Text("Country") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFFFF8C42),
                    selectedLabelColor = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (viewModel.isTabsLoading) {
            Box(
                modifier = Modifier.fillMaxWidth().height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFFFF8C42))
            }
        } else {

            if (showCategories) {
                Text("Categories", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFFFF8C42))
                HorizontalDivider(color = Color(0xFFFFE0CC))
                Spacer(modifier = Modifier.height(8.dp))
                viewModel.filteredCategories.chunked(2).forEach { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        row.forEach { category ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(110.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .clickable {
                                        navController.navigate(
                                            Routes.MealsList("category", category.strCategory)
                                        )
                                    }
                            ) {
                                AsyncImage(
                                    model = category.strCategoryThumb,
                                    contentDescription = category.strCategory,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                                Box(
                                    modifier = Modifier
                                        .matchParentSize()
                                        .background(
                                            Brush.verticalGradient(
                                                listOf(Color.Transparent, Color.Black.copy(0.6f))
                                            )
                                        )
                                )
                                Text(
                                    text = category.strCategory,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.align(Alignment.BottomStart).padding(8.dp)
                                )
                            }
                        }
                        if (row.size == 1) Spacer(modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            if (showCountries) {
                Text("Countries", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFFFF8C42))
                HorizontalDivider(color = Color(0xFFFFE0CC))
                Spacer(modifier = Modifier.height(8.dp))
                viewModel.filteredCountries.chunked(3).forEach { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        row.forEach { country ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable {
                                        navController.navigate(
                                            Routes.MealsList("country", country.strArea)
                                        )
                                    }
                                    .padding(8.dp)
                            ) {
                                Text(getFlagEmoji(country.strArea), fontSize = 36.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    country.strArea,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.DarkGray
                                )
                            }
                        }
                        repeat(3 - row.size) { Spacer(modifier = Modifier.weight(1f)) }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            if (showIngredients) {
                Text("Ingredients", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFFFF8C42))
                HorizontalDivider(color = Color(0xFFFFE0CC))
                Spacer(modifier = Modifier.height(8.dp))
                viewModel.filteredIngredients.chunked(3).forEach { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        row.forEach { ingredient ->
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color(0xFFFFF3E6))
                                    .border(1.dp, Color(0xFFFFE0CC), RoundedCornerShape(16.dp))
                                    .clickable {
                                        navController.navigate(
                                            Routes.MealsList("ingredient", ingredient.strIngredient)
                                        )
                                    }
                                    .padding(vertical = 12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AsyncImage(
                                    model = "https://www.themealdb.com/images/ingredients/${ingredient.strIngredient}-Small.png",
                                    contentDescription = ingredient.strIngredient,
                                    modifier = Modifier.size(50.dp)
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    ingredient.strIngredient,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.DarkGray
                                )
                            }
                        }
                        repeat(3 - row.size) { Spacer(modifier = Modifier.weight(1f)) }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            if (!showCategories && !showCountries && !showIngredients) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(top = 80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🔍", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Choose a filter to explore meals", color = Color.Gray, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}