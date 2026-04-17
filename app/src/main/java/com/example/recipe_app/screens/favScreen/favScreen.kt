package com.example.recipe_app.screens.favScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.ui.tooling.preview.Preview

data class Meal(
    val id: String,
    val title: String,
    val category: String,
    val area: String,
    val imageUrl: String,
    val isFavorite: Boolean
)

class FavViewModel : ViewModel() {

    private val _favorites = MutableStateFlow<List<Meal>>(emptyList())
    val favorites: StateFlow<List<Meal>> = _favorites

    fun loadFavorites(meals: List<Meal>) {
        _favorites.value = meals.filter { it.isFavorite }
    }

    fun removeFromFavorites(meal: Meal) {
        _favorites.value = _favorites.value.filter { it.id != meal.id }
    }
}

@Composable
fun FavoriteScreen(
    viewModel: FavViewModel
) {
    val meals by viewModel.favorites.collectAsState()
    var selectedMeal by remember { mutableStateOf<Meal?>(null) }

    FavoriteScreenContent(
        meals = meals,
        onRemoveClick = { selectedMeal = it }
    )

    selectedMeal?.let { meal ->
        RemoveDialog(
            onDismiss = { selectedMeal = null },
            onConfirm = {
                viewModel.removeFromFavorites(meal)
                selectedMeal = null
            }
        )
    }
}

@Composable
fun FavoriteScreenContent(
    meals: List<Meal>,
    onRemoveClick: (Meal) -> Unit
) {

    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "My Favorites",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(meals) { meal ->
                FavoriteItem(
                    meal = meal,
                    onClickFav = { onRemoveClick(meal) }
                )
            }
        }
    }
}

@Composable
fun FavoriteItem(
    meal: Meal,
    onClickFav: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Box {

            Column {

                AsyncImage(
                    model = meal.imageUrl.ifEmpty { "https://via.placeholder.com/300" },
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
                        text = meal.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "${meal.category} • ${meal.area}",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }

            IconButton(
                onClick = onClickFav,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .background(Color.White, CircleShape)
                    .size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Color(0xFFFF7043)
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
                    containerColor = Color(0xFFFF7043)
                )
            ) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("No")
            }
        },
        title = {
            Text("Remove from Favorites?")
        },
        text = {
            Text("Are you sure you want to remove this recipe from your collection?")
        },
        shape = RoundedCornerShape(20.dp)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FavoriteScreenPreview() {

    val fakeMeals = listOf(
        Meal(
            id = "1",
            title = "Pizza",
            category = "Italian",
            area = "Italy",
            imageUrl = "",
            isFavorite = true
        ),
        Meal(
            id = "2",
            title = "Burger",
            category = "Fast Food",
            area = "USA",
            imageUrl = "",
            isFavorite = true
        )
    )

    FavoriteScreenContent(
        meals = fakeMeals,
        onRemoveClick = {}
    )
}