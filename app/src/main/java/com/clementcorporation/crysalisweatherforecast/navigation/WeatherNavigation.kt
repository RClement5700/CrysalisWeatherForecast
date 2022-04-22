package com.clementcorporation.crysalisweatherforecast.navigation

import SearchScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.clementcorporation.crysalisweatherforecast.screens.about.AboutScreen
import com.clementcorporation.crysalisweatherforecast.screens.favorites.FavoritesScreen
import com.clementcorporation.crysalisweatherforecast.screens.favorites.FavoritesViewModel
import com.clementcorporation.crysalisweatherforecast.screens.main.MainScreen
import com.clementcorporation.crysalisweatherforecast.screens.splash.WeatherSplashScreen
import com.clementcorporation.crysalisweatherforecast.screens.main.MainViewModel
import com.clementcorporation.crysalisweatherforecast.screens.settings.SettingsScreen
import com.clementcorporation.crysalisweatherforecast.util.DEFAULT_CITY

private const val ROUTE_KEYWORD = "city"
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }
        val route = WeatherScreens.MainScreen.name
        composable(
            "$route/{$ROUTE_KEYWORD}",
            arguments = listOf(
                navArgument(
                    name = ROUTE_KEYWORD,
                    builder = {
                        type = NavType.StringType
                    }
                )
            )
        ) { navBack ->
            navBack.arguments?.getString(ROUTE_KEYWORD, DEFAULT_CITY).let { cityName ->
                val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(navController = navController, mainViewModel = mainViewModel, cityName = cityName)
            }
        }
        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }
        composable(WeatherScreens.AboutScreen.name) {
            AboutScreen(navController = navController)
        }
        composable(WeatherScreens.FavoriteScreen.name) {
            FavoritesScreen(navController = navController)
        }
        composable(WeatherScreens.SettingsScreen.name) {
            SettingsScreen(navController = navController)
        }
    }
}