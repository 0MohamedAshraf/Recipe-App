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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.recipe_app.database.db.FavoriteDatabase
import com.example.recipe_app.network.ApiClient
import com.example.recipe_app.network.RemoteDataSourceImpl
import com.example.recipe_app.screens.detailsScreen.DetailsScreen
import com.example.recipe_app.screens.detailsScreen.components.DetailsBottomNav
import com.example.recipe_app.screens.detailsScreen.components.DetailsTopBar
import com.example.recipe_app.screens.detailsScreen.repo.MealDetailsRepoImpl
import com.example.recipe_app.screens.detailsScreen.viewmodel.DetailsViewModelFactory
import com.example.recipe_app.screens.detailsScreen.viewmodel.MealDetailsViewModel
import com.example.recipe_app.screens.favScreen.FavoriteScreen
import com.example.recipe_app.screens.favScreen.viewmodel.FavoriteViewModel
import com.example.recipe_app.screens.favScreen.viewmodel.FavoriteViewModelFactory
import com.example.recipe_app.screens.homeScreen.HomeScreen
import com.example.recipe_app.screens.homeScreen.components.HomeTopBar
import com.example.recipe_app.screens.homeScreen.repo.MealRepositoryImpl
import com.example.recipe_app.screens.homeScreen.viewmodel.MealViewModel
import com.example.recipe_app.screens.homeScreen.viewmodel.MealViewModelFactory
import com.example.recipe_app.screens.profileScreen.ProfileScreen
import com.example.recipe_app.screens.profileScreen.components.ProfileTopBar
import com.example.recipe_app.screens.searchScreen.SearchScreen
import com.example.recipe_app.screens.searchScreen.components.SearchTopBar
import com.example.recipe_app.screens.signInScreen.SignInScreen
import com.example.recipe_app.screens.signInScreen.viewmodel.SignInViewModel
import com.example.recipe_app.screens.signInScreen.viewmodel.SignInViewModelFactory
import com.example.recipe_app.screens.signUpScreen.SignUpScreen
import com.example.recipe_app.screens.splashScreen.SplashScreen
import com.example.recipe_app.screens.splashScreen.viewmodel.SplashViewModel
import com.example.recipe_app.screens.splashScreen.viewmodel.SplashViewModelFactory
import com.example.recipe_app.service.AccountServiceImpl
import com.example.recipe_app.ui.theme.OrangeVariant
import com.example.recipe_app.ui.theme.Recipe_AppTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

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

    val mealViewModel: MealViewModel by viewModels {
        MealViewModelFactory(
            repository = MealRepositoryImpl(
                remoteDataSource = RemoteDataSourceImpl(
                    apiService = ApiClient.service
                )
            )
        )
    }
    val favViewModel : FavoriteViewModel by viewModels {
        FavoriteViewModelFactory(
            favoriteDao = FavoriteDatabase.getInstance(this).favoriteDao(),
            accountService = AccountServiceImpl()
        )
    }
    val detailsViewModel : MealDetailsViewModel by viewModels {
        DetailsViewModelFactory(
            detailsRepo = MealDetailsRepoImpl(
                remoteDataSource = RemoteDataSourceImpl(
                    apiService = ApiClient.service
                )
            ),
            favoriteDao = FavoriteDatabase.getInstance(this).favoriteDao(),
            accountService = AccountServiceImpl()
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Recipe_AppTheme {
                val navController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentScreen = backStackEntry?.destination
                val isFav by detailsViewModel.isFavorite.collectAsStateWithLifecycle()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        when {
                            currentScreen?.hasRoute<Routes.Home>() == true -> HomeTopBar()
                            currentScreen?.hasRoute<Routes.Details>() == true -> {
                                DetailsTopBar(
                                    onBackClick = {navController.popBackStack()},
                                    onFavoriteClick = {
                                        if(isFav){
                                            detailsViewModel.removeFromFavorites()
                                        }else{
                                        detailsViewModel.addToFavorites()
                                        }
                                    },
                                    onShareClick = {},
                                    isFavorite = isFav
                                )
                            }
                            currentScreen?.hasRoute<Routes.Profile>() == true -> {
                                ProfileTopBar(
                                    onBackClick = {navController.popBackStack() },
                                    onSettingsClick = {}
                                )
                            }
                            currentScreen?.hasRoute<Routes.Search>() == true -> {
                                SearchTopBar(
                                    onBackClick = { navController.popBackStack() },
                                    onMicClick = {}
                                )
                            }
                            else -> {}

                        }
                    },
                    bottomBar = {
                        when{
                            currentScreen?.hasRoute<Routes.Home>() == true ||
                            currentScreen?.hasRoute<Routes.Profile>() == true ||
                            currentScreen?.hasRoute<Routes.Search>() == true ||
                            currentScreen?.hasRoute<Routes.Favorites>() == true -> {
                                val selectedIndex : Int = when{
                                    currentScreen.hasRoute<Routes.Home>() -> 0
                                    currentScreen.hasRoute<Routes.Search>() -> 1
                                    currentScreen.hasRoute<Routes.Favorites>() -> 2
                                    currentScreen.hasRoute<Routes.Profile>() -> 3
                                    else -> 0
                                }
                                NavigationBar {
                                    navBarItems.forEachIndexed { index, item ->
                                        NavigationBarItem(
                                            selected = selectedIndex == index,
                                            onClick = {
                                                when(index){
                                                    0 -> navController.navigate(Routes.Home)
                                                    1 -> navController.navigate(Routes.Search)
                                                    2 -> navController.navigate(Routes.Favorites)
                                                    3 -> navController.navigate(Routes.Profile("id"))
                                                }
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
                            currentScreen?.hasRoute<Routes.Details>() == true ->
                                NavigationBar{DetailsBottomNav({})}
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
                            val splashViewModel : SplashViewModel = viewModel(
                                factory = SplashViewModelFactory(
                                    service = AccountServiceImpl()
                                )
                            )
                            SplashScreen(
                                modifier = screenModifier,
                                splashViewModel = splashViewModel,
                                navigateToMain = {
                                    navController.navigate(route = MainGraph) {
                                        popUpTo<Routes.Splash> { inclusive = true }
                                        launchSingleTop = true
                                    }
                                },
                                navigateToAuth = {
                                    navController.navigate(route = AuthGraph) {
                                        popUpTo<Routes.Splash> { inclusive = true }
                                        launchSingleTop = true
                                    }
                                }
                            )

                        }

                        navigation<AuthGraph>(startDestination = Routes.SignIn) {

                            composable<Routes.SignIn> {

                                val signInViewModel : SignInViewModel = viewModel(
                                    factory = SignInViewModelFactory(
                                        service = AccountServiceImpl()
                                    )
                                )


                                SignInScreen(
                                    modifier = screenModifier,
                                    onSignUp = {
                                        navController.navigate(route = Routes.SignUp)
                                    },
                                    onLogin = {
                                        Log.d("abc -> ", "User: ${Firebase.auth.currentUser?.displayName}")
                                        navController.navigate(route = MainGraph) {
                                            popUpTo<AuthGraph> {
                                                inclusive = true
                                            }
                                            launchSingleTop = true
                                        }
                                    },
                                    signInViewModel = signInViewModel
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

                                mealViewModel.getMealByCategory("Beef")

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


                                LaunchedEffect(mealId) {
                                    detailsViewModel.getMealById(mealId)
                                }
                                DetailsScreen(
                                    detailsViewModel = detailsViewModel,
                                    modifier = screenModifier
                                )
                            }

                            composable<Routes.Profile> { userId ->
                                ProfileScreen(
                                    modifier = screenModifier,
                                    onLogoutClick = {
                                        Firebase.auth.signOut()
                                        navController.navigate(AuthGraph){
                                            popUpTo<MainGraph>{
                                                inclusive = true
                                            }
                                        }
                                    }
                                )
                            }

                            composable<Routes.Search>{
                                SearchScreen(screenModifier)
                            }
                            composable<Routes.Favorites>{


                                favViewModel.getAllFavorites()

                                FavoriteScreen(
                                    favViewModel,
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