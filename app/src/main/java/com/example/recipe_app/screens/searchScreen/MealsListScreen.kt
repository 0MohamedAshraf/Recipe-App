package com.example.recipe_app.screens.searchScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.recipe_app.Routes
import com.example.recipe_app.screens.searchScreen.viewmodel.MealsListViewModel
import kotlin.collections.isNotEmpty

@Composable
fun MealsListScreen(
    modifier: Modifier = Modifier,
    filterType: String,
    filterValue: String,
    navController: NavController,
    viewModel: MealsListViewModel

) {
    var searchValue by remember { mutableStateOf("") }

    LaunchedEffect(filterType, filterValue) {
        viewModel.loadMeals(filterType, filterValue)
    }

    LaunchedEffect(searchValue) {
        viewModel.filterMeals(searchValue)
    }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = filterValue,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = searchValue,
                onValueChange = { searchValue = it },
                placeholder = { Text("Search in $filterValue...") },
                shape = RoundedCornerShape(28.dp),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        tint = Color(0xFFFF8C42)
                    )
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

            when {
                viewModel.isLoading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFFFF8C42))
                    }
                }
                viewModel.errorMessage != null -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(viewModel.errorMessage!!, color = Color.Red)
                    }
                }
                viewModel.meals.isNotEmpty() -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(viewModel.meals) { meal ->
                            MealResultItem(meal = meal,
                                onClick = {
                                    navController.navigate(Routes.Details(meal.idMeal))
                                })
                        }
                    }
                }
                else -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No meals found!", color = Color.Gray, fontSize = 16.sp)
                    }
                }
            }
        }

}