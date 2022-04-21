package com.clementcorporation.crysalisweatherforecast.screens.settings

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.clementcorporation.crysalisweatherforecast.widgets.WeatherAppBar

@Composable
fun SettingsScreen(navController: NavController, settingsViewModel: SettingsViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
            WeatherAppBar(navController = navController)
        }
    ) {
        
    }
}