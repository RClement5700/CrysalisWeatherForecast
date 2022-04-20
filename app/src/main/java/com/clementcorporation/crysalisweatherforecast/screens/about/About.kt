package com.clementcorporation.crysalisweatherforecast.screens.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.clementcorporation.crysalisweatherforecast.R
import com.clementcorporation.crysalisweatherforecast.util.BASE_URL
import com.clementcorporation.crysalisweatherforecast.widgets.WeatherAppBar

private const val TITLE = "About"
@Composable
fun AboutScreen(navController: NavController) {
    Scaffold(
        backgroundColor = Color.White,
        topBar = {
            WeatherAppBar(
                title = TITLE,
                icon = Icons.Default.ArrowBack,
                isMainScreen = false,
                navController = navController,
                onButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
    ) {
        Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.about),
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = BASE_URL,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}