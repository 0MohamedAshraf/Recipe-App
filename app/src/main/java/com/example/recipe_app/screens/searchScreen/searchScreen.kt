package com.example.recipe_app.screens.searchScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipe_app.R

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp).verticalScroll(rememberScrollState())
        ) {



            var searchValue by remember { mutableStateOf("") }

            OutlinedTextField(
                value = searchValue,
                onValueChange = { searchValue = it },
                placeholder = { Text("Pasta, Avocado, Italian...") },
                shape = RoundedCornerShape(size = 28.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Categories",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Text(
                    text = "See all",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFFFF8C42),
                    modifier = Modifier.clickable{Toast.makeText(context,"See all categories", Toast.LENGTH_SHORT).show()}
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(Modifier.fillMaxWidth()) {
                CategoryItem(title = "Breakfast", image =R.drawable.breakfast, Modifier.weight(1f))
                Spacer(modifier = Modifier.width(8.dp))
                CategoryItem(title = "Lunch", image = R.drawable.lunch, Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(Modifier.fillMaxWidth()) {
                CategoryItem(title = "Dinner", image = R.drawable.dinner, Modifier.weight(1f))
                Spacer(modifier = Modifier.width(8.dp))
                CategoryItem(title = "Desserts", image = R.drawable.dessert, Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Cuisines", fontWeight = FontWeight.Bold, fontSize = 18.sp)

            Spacer(modifier = Modifier.height(12.dp))

            Row(Modifier.fillMaxWidth()) {
                CuisineItem(title = "Italian", image = R.drawable.italian, Modifier.weight(1f))
                CuisineItem(title ="Mexican", image = R.drawable.mexican, Modifier.weight(1f))
                CuisineItem(title ="Japanese", image = R.drawable.japanese, Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(Modifier.fillMaxWidth()) {
                CuisineItem(title ="Indian", image =R.drawable.indian, Modifier.weight(1f))
                CuisineItem(title ="French", image =R.drawable.french, Modifier.weight(1f))
                CuisineItem(title ="Greek", image =R.drawable.greek, Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Popular Ingredients", fontWeight = FontWeight.Bold, fontSize = 18.sp)

            Spacer(modifier = Modifier.height(12.dp))

            Row(Modifier.fillMaxWidth()) {
                IngredientItem(title ="Avocado", image =R.drawable.avocado, Modifier.weight(1f))
                IngredientItem(title ="Chicken", image =R.drawable.chicken, Modifier.weight(1f))
                IngredientItem(title ="Tomato", image =R.drawable.tomato, Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(Modifier.fillMaxWidth()) {
                IngredientItem(title ="Beef", image = R.drawable.beef, Modifier.weight(1f))
                IngredientItem(title ="Mushroom", image =R.drawable.mushroom, Modifier.weight(1f))
                IngredientItem(title ="Broccoli", image =R.drawable.broccoli, Modifier.weight(1f))
            }
        }

}


@Composable
fun CategoryItem(title: String, image: Int, modifier: Modifier) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .height(100.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
                    )
                ).clickable
                {
                    Toast.makeText(context,"Clicked", Toast.LENGTH_SHORT).show()

                }
        )

        Text(
            text = title,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp)
        )
    }
}

@Composable
fun CuisineItem(title: String, image: Int, modifier: Modifier) {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .border(3.dp, Color(0xFFFFE0CC), CircleShape).clickable{
                    Toast.makeText(context,"Clicked", Toast.LENGTH_SHORT).show()
                }
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun IngredientItem(title: String, image: Int, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .padding(6.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFFFF3E6))
            .border(1.dp, Color(0xFFFFE0CC), RoundedCornerShape(16.dp))
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = title,
            modifier = Modifier.size(32.dp).clickable{
                Toast.makeText(context,"Clicked", Toast.LENGTH_SHORT).show()
            }
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(title, fontSize = 12.sp, color = Color.DarkGray)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewScreen() {
    SearchScreen()
}

