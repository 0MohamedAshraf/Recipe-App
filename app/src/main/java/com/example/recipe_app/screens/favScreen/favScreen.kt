package com.example.recipe_app.screens.favScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.recipe_app.database.entity.Favorite
import com.example.recipe_app.screens.favScreen.viewmodel.FavoriteViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel,
    modifier: Modifier = Modifier
) {
    val favMeals by viewModel.favoriteMeals.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }
    // State to track which meal ID to delete
    var selectedMealId by remember { mutableStateOf<String?>(null) }
    FavoriteScreenContent(
        meals = favMeals,
        onRemoveClick = { mealId ->
            selectedMealId = mealId
            showDialog = true
        },
        modifier = modifier
    )

    // Show dialog based on the state
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

@Composable
fun FavoriteScreenContent(
    meals: List<Favorite>,
    onRemoveClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Log.d("meals: ", "FavoriteScreenContent: $meals ")
    Column(modifier = modifier.fillMaxSize()) {


        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(meals, key = {meal -> meal.id}) { meal ->
                FavoriteItem(
                    meal = meal,
                    onClickFav = { onRemoveClick(meal.id) }
                )
            }
        }
    }
}

@Composable
fun FavoriteItem(
    meal: Favorite,
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


}