package com.example.recipe_app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

// COLORS
val PrimaryOrange = Color(0xFFFF6B3D)
val LightGrayBg = Color(0xFFF8F8F8)
val CardWhite = Color(0xFFFFFFFF)
val TextPrimary = Color(0xFF1C1C1C)
val TextSecondary = Color(0xFF8A8A8A)

// DATA
data class FoodItem(
    val id: Int,
    val title: String,
    val category: String,
    val imageUrl: String
)

// SCREEN
@Composable
fun FavoriteScreen() {

    val list = remember {
        mutableStateListOf(
            FoodItem(1, "Classic Beef Burger", "Beef • American", ""),
            FoodItem(2, "Zesty Salmon Salad", "Seafood • Mediterranean", ""),
            FoodItem(3, "Spaghetti Carbonara", "Pasta • Italian", "")
        )
    }

    var showDialog by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<FoodItem?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGrayBg)
    ) {

        // TOP BAR
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = TextPrimary,
                modifier = Modifier.clickable { }
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "My Favorites",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            )
        }

        // LIST
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
        ) {
            items(list) { item ->
                FoodCard(item) {
                    selectedItem = item
                    showDialog = true
                }
            }
        }
    }

    // DIALOG
    if (showDialog && selectedItem != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        list.remove(selectedItem!!)
                        showDialog = false
                    }
                ) {
                    Text("Remove")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            },
            title = { Text("Remove from Favorites?") },
            text = { Text("This item will be removed.") }
        )
    }
}

// CARD
@Composable
fun FoodCard(item: FoodItem, onFavClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {

        Column {

            Box {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                        .background(Color.Gray)
                )

                IconButton(
                    onClick = onFavClick,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(38.dp)
                        .align(Alignment.TopEnd)
                        .clip(CircleShape)
                        .background(CardWhite)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = PrimaryOrange
                    )
                }
            }

            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = item.category,
                    color = TextSecondary
                )
            }
        }
    }
}

// PREVIEW
@Preview(showBackground = true)
@Composable
fun PreviewFavorite() {
    FavoriteScreen()
}