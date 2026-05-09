package com.example.recipe_app.screens.favScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.recipe_app.R
import com.example.recipe_app.database.entity.Favorite
import com.example.recipe_app.screens.favScreen.viewmodel.FavoriteViewModel

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel,
    onMealClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val favMeals by viewModel.favoriteMeals.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }
    var selectedMealId by remember { mutableStateOf<String?>(null) }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (favMeals.isEmpty()) {
            NoFavoriteScreen()
        } else {
            FavoriteScreenContent(
                meals = favMeals,
                onRemoveClick = { mealId ->
                    selectedMealId = mealId
                    showDialog = true
                },
                onMealClick = onMealClick
            )

            if (showDialog) {
                RemoveDialog(
                    onDismiss = {
                        showDialog = false
                        selectedMealId = null
                    },
                    onConfirm = {
                        selectedMealId?.let { mealId ->
                            viewModel.removeFavorite(mealId)
                        }
                        showDialog = false
                        selectedMealId = null
                    }
                )
            }
        }
    }
}

@Composable
fun FavoriteScreenContent(
    meals: List<Favorite>,
    onRemoveClick: (String) -> Unit,
    onMealClick: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(meals, key = { it.id }) { meal ->
            FavoriteItem(
                meal = meal,
                onClickFav = { onRemoveClick(meal.id) },
                onMealClick = { onMealClick(meal.id) }
            )
        }
    }
}

@Composable
fun FavoriteItem(
    meal: Favorite,
    onMealClick: () -> Unit,
    onClickFav: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth(),
        onClick = onMealClick,
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Box {
            Column {
                AsyncImage(
                    model = meal.image.ifEmpty { "https://via.placeholder.com/300" },
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )

                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(
                        text = meal.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "${meal.category} • ${meal.area}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            IconButton(
                onClick = onClickFav,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f), CircleShape)
                    .size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun RemoveDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(stringResource(R.string.yes))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.no), color = MaterialTheme.colorScheme.secondary)
            }
        },
        title = {
            Text(
                text = stringResource(R.string.remove_from_favorites),
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Text(
                text = stringResource(R.string.are_you_sure_you_want_to_remove_this_recipe),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(24.dp)
    )
}
