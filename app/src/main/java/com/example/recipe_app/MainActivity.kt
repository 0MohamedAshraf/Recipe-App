package com.example.recipe_app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.recipe_app.network.ApiClient
import com.example.recipe_app.network.RemoteDataSourceImpl
import com.example.recipe_app.screens.detailsScreen.DetailsScreen
import com.example.recipe_app.screens.detailsScreen.components.DetailsTopBar
import com.example.recipe_app.screens.detailsScreen.repo.MealDetailsRepoImpl
import com.example.recipe_app.screens.detailsScreen.viewmodel.DetailsViewModelFactory
import com.example.recipe_app.screens.detailsScreen.viewmodel.MealDetailsViewModel
import com.example.recipe_app.screens.homeScreen.HomeScreen
import com.example.recipe_app.screens.homeScreen.components.HomeTopBar
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

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Recipe_AppTheme {
                val navController = rememberNavController()
                val auth = FirebaseAuth.getInstance()

                var selectedIndex by rememberSaveable { mutableStateOf(0) }
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentScreen = backStackEntry?.destination
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                            when {
                                currentScreen?.hasRoute<Routes.Home>() == true -> HomeTopBar()
                                currentScreen?.hasRoute<Routes.Details>() == true -> {
                                    DetailsTopBar(
                                        onBackClick = {navController.popBackStack()},
                                        onFavoriteClick = {},
                                        onShareClick = {}
                                    )
                                }
                                else -> {}

                            }
                    },
                    bottomBar = {
                        when{
                           currentScreen?.hasRoute<Routes.Home>() == true -> {
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
                            else -> {}
                        }
                    }
                ) { innerPadding ->

                    val screenModifier = Modifier.padding(innerPadding)

                    NavHost(
                        navController = navController,
                        startDestination = Routes.Splash
                    ) {

                        composable<Routes.Splash> {

                            SplashScreenAnimation(
                                modifier = screenModifier,
                                onAnimationEnd = {
                                    val currentUser = auth.currentUser

                                    if (currentUser != null) {
                                        currentUser.reload().addOnCompleteListener {
                                            val updatedUser = auth.currentUser

                                            if (updatedUser != null && updatedUser.isEmailVerified) {
                                                navController.navigate(route = MainGraph) {
                                                    popUpTo<Routes.Splash> {
                                                        inclusive = true
                                                    }
                                                    launchSingleTop = true
                                                }
                                            } else {
                                                auth.signOut()
                                                navController.navigate(route = AuthGraph) {
                                                    popUpTo<Routes.Splash> {
                                                        inclusive = true
                                                    }
                                                    launchSingleTop = true
                                                }
                                            }
                                        }
                                    } else {
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

                                SignInScreen(
                                    modifier = screenModifier,
                                    onSignUp = {
                                        navController.navigate(route = Routes.SignUp)
                                    },
                                    onLogin = {
                                        navController.navigate(route = MainGraph) {
                                            popUpTo<AuthGraph> {
                                                inclusive = true
                                            }
                                            launchSingleTop = true
                                        }
                                    },
                                    onContinueAsGuest = {
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
                                val mealViewModel: MealViewModel by viewModels {
                                    MealViewModelFactory(
                                        repository = MealRepositoryImpl(
                                            remoteDataSource = RemoteDataSourceImpl(
                                                apiService = ApiClient.service
                                            )
                                        )
                                    )
                                }
                                HomeScreen(
                                    mealViewModel = mealViewModel,
                                    modifier = screenModifier,
                                    onMealClick = { mealId ->
                                        Log.d("abc --> ", "Home: {$mealId}")
                                        navController.navigate(Routes.Details(mealId))
                                    }
                                )

                            }
                            composable<Routes.Details>{ backStackEntry ->
                                val mealId = backStackEntry.toRoute<Routes.Details>().id
                                Log.d("abc --> ", "Details: {$mealId}")

                                val detailsViewModel : MealDetailsViewModel by viewModels {
                                    DetailsViewModelFactory(
                                        detailsRepo = MealDetailsRepoImpl(
                                            remoteDataSource = RemoteDataSourceImpl(
                                                apiService = ApiClient.service
                                            )
                                        )
                                    )
                                }
                                detailsViewModel.getMealById(mealId)
                                DetailsScreen(
                                    detailsViewModel = detailsViewModel,
                                    modifier = screenModifier
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}