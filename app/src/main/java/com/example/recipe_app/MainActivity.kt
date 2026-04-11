package com.example.recipe_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.recipe_app.network.ApiClient
import com.example.recipe_app.network.RemoteDataSourceImpl
import com.example.recipe_app.screens.homeScreen.HomeScreen
import com.example.recipe_app.screens.homeScreen.repo.MealRepositoryImpl
import com.example.recipe_app.screens.homeScreen.viewmodel.MealViewModel
import com.example.recipe_app.screens.homeScreen.viewmodel.MealViewModelFactory
import com.example.recipe_app.screens.signInScreen.SignInScreen
import com.example.recipe_app.screens.splashScreen.SplashScreenAnimation
import com.example.recipe_app.ui.theme.OrangeVariant
import com.example.recipe_app.ui.theme.Recipe_AppTheme
import com.google.firebase.auth.FirebaseAuth

data class BottomNavBarItem(
    val label: String,
    val icon: ImageVector
)

val navBarItems = listOf(
    BottomNavBarItem("HOME", Icons.Outlined.Home),
    BottomNavBarItem("SEARCH", Icons.Outlined.Search),
    BottomNavBarItem("FAVORITES", Icons.Outlined.FavoriteBorder),
    BottomNavBarItem("PROFILE", Icons.Outlined.Person)
)

class MainActivity : ComponentActivity() {

    private val mealViewModel: MealViewModel by viewModels {
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
                val navController = rememberNavController()
                val auth = FirebaseAuth.getInstance()

                var selectedIndex by rememberSaveable { mutableStateOf(0) }
                var showBars by rememberSaveable { mutableStateOf(false) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        if (showBars) {
                            TopAppBar(
                                title = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            painter = painterResource(R.drawable.spoon_knife_icon),
                                            contentDescription = "Title Icon",
                                            tint = OrangeVariant
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
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
                            )
                        }
                    },
                    bottomBar = {
                        if (showBars) {
                            NavigationBar {
                                navBarItems.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        selected = selectedIndex == index,
                                        onClick = {
                                            selectedIndex = index
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = item.icon,
                                                contentDescription = item.label,
                                                tint = if (selectedIndex == index) {
                                                    OrangeVariant
                                                } else {
                                                    LocalContentColor.current
                                                }
                                            )
                                        },
                                        label = { Text(item.label) }
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->

                    val screenModifier = Modifier.padding(innerPadding)

                    NavHost(
                        navController = navController,
                        startDestination = Routes.Splash
                    ) {

                        composable<Routes.Splash> {
                            showBars = false

                            SplashScreenAnimation(
                                modifier = screenModifier,
                                onAnimationEnd = {
                                    val currentUser = auth.currentUser

                                    if (currentUser != null) {
                                        currentUser.reload().addOnCompleteListener {
                                            val updatedUser = auth.currentUser

                                            if (updatedUser != null && updatedUser.isEmailVerified) {
                                                showBars = true
                                                navController.navigate(route = MainGraph) {
                                                    popUpTo<Routes.Splash> {
                                                        inclusive = true
                                                    }
                                                    launchSingleTop = true
                                                }
                                            } else {
                                                auth.signOut()
                                                showBars = false
                                                navController.navigate(route = AuthGraph) {
                                                    popUpTo<Routes.Splash> {
                                                        inclusive = true
                                                    }
                                                    launchSingleTop = true
                                                }
                                            }
                                        }
                                    } else {
                                        showBars = false
                                        navController.navigate(route = AuthGraph) {
                                            popUpTo<Routes.Splash> {
                                                inclusive = true
                                            }
                                            launchSingleTop = true
                                        }
                                    }
                                }
                            )
                        }

                        navigation<AuthGraph>(startDestination = Routes.SignIn) {

                            composable<Routes.SignIn> {
                                showBars = false

                                SignInScreen(
                                    modifier = screenModifier,
                                    onSignUp = {
                                        navController.navigate(route = Routes.SignUp)
                                    },
                                    onLogin = {
                                        showBars = true
                                        navController.navigate(route = MainGraph) {
                                            popUpTo<AuthGraph> {
                                                inclusive = true
                                            }
                                            launchSingleTop = true
                                        }
                                    },
                                    onContinueAsGuest = {
                                        showBars = true
                                        navController.navigate(route = MainGraph) {
                                            popUpTo<AuthGraph> {
                                                inclusive = true
                                            }
                                            launchSingleTop = true
                                        }
                                    }
                                )
                            }

                            composable<Routes.SignUp> {
                                showBars = false

                                SignUpScreen(
                                    modifier = screenModifier,
                                    onLoginClick = {
                                        navController.popBackStack()
                                    }
                                )
                            }
                        }

                        navigation<MainGraph>(startDestination = Routes.Home) {

                            composable<Routes.Home> {
                                showBars = true

                                HomeScreen(
                                    mealViewModel = mealViewModel,
                                    modifier = screenModifier
                                )

                                with(mealViewModel) {
                                    getRandomMeal()
                                    getAllCategories()
                                    getMealByCategory("Beef")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}