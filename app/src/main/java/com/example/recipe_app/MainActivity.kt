package com.example.recipe_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipe_app.dataSource.network.ApiClient
import com.example.recipe_app.dataSource.network.RemoteDataSource
import com.example.recipe_app.dataSource.network.RemoteDataSourceImpl
import com.example.recipe_app.repo.MealRepository
import com.example.recipe_app.repo.MealRepositoryImpl
import com.example.recipe_app.screens.HomeScreen
import com.example.recipe_app.screens.SplashScreenAnimation
import com.example.recipe_app.ui.theme.OrangeVariant
import com.example.recipe_app.ui.theme.Recipe_AppTheme

data class BottomNavBarItem(
    val label: String,
    val icon: ImageVector
)
val NavBarItems = listOf(
    BottomNavBarItem("HOME", Icons.Outlined.Home),
    BottomNavBarItem("SEARCH", Icons.Outlined.Search),
    BottomNavBarItem("FAVORITES", Icons.Outlined.FavoriteBorder),
    BottomNavBarItem("PROFILE", Icons.Outlined.Person)

)
class MainActivity : ComponentActivity() {
    private val mealViewModel : MealViewModel by viewModels {
        MealViewModelFactory(
            repository = MealRepositoryImpl(
                remoteDataSource = RemoteDataSourceImpl(
                    apiService = ApiClient.service
                )
            )
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Recipe_AppTheme {
                var selectedIndex by rememberSaveable { mutableStateOf(0) }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { TopAppBar(
                        title = {
                            Row (
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Icon(
                                    painter = painterResource(R.drawable.spoon_knife_icon),
                                    contentDescription = "Title Icon",
                                    tint = OrangeVariant
                                )

                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = "RecipeHome",
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        },
                        actions = {
                            Icon(
                                imageVector = Icons.Outlined.Notifications,
                                contentDescription = "Notifications",
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        }


                    ) },
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

                   SplashScreenAnimation(
                      modifier = Modifier.padding(innerPadding)
                   )

                }
            }
        }
    }
}


