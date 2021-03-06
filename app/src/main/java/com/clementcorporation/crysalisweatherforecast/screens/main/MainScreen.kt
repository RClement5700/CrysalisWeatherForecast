package com.clementcorporation.crysalisweatherforecast.screens.main

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.clementcorporation.crysalisweatherforecast.data.DataOrException
import com.clementcorporation.crysalisweatherforecast.model.Weather
import com.clementcorporation.crysalisweatherforecast.model.WeatherItem
import com.clementcorporation.crysalisweatherforecast.navigation.WeatherScreens
import com.clementcorporation.crysalisweatherforecast.screens.settings.SettingsViewModel
import com.clementcorporation.crysalisweatherforecast.util.DEFAULT_CITY
import com.clementcorporation.crysalisweatherforecast.util.formatDate
import com.clementcorporation.crysalisweatherforecast.util.formatDecimals
import com.clementcorporation.crysalisweatherforecast.widgets.*

private const val TAG = "MainScreen"
private const val DEFAULT_UNITS = "imperial"
private const val SUBTITLE = "This Week"
@Composable
fun MainScreen(navController: NavController,
               mainViewModel: MainViewModel = hiltViewModel(),
               settingsViewModel: SettingsViewModel = hiltViewModel(),
               cityName: String?) {

    val unitFromDb = settingsViewModel.unitList.collectAsState().value
    var unit by remember {
        mutableStateOf(DEFAULT_UNITS)
    }
    var isImperial by remember {
        mutableStateOf(false)
    }
    if (!unitFromDb.isNullOrEmpty()) {
        unit = unitFromDb.first().unit.split(" ").first().lowercase()
        isImperial = unit == DEFAULT_UNITS
        val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
            initialValue = DataOrException(loading = true), producer = {
                value = mainViewModel.getWeatherData(city = cityName ?: DEFAULT_CITY, units = unit)
            }
        ).value
        if (weatherData.loading == true) {
            CircularProgressIndicator()
        } else if (weatherData.data != null) {
            MainScaffold(weather = weatherData.data!!, navController = navController, isImperial = isImperial)
        }
    }
}

@Composable
fun MainScaffold(weather: Weather, navController: NavController, isImperial: Boolean) {
    Scaffold(
        backgroundColor = Color.White,
        topBar = {
            WeatherAppBar(
                title = weather.city.name + ", ${weather.city.country}",
                navController = navController,
                elevation = 5.dp,
                onAddActionClicked = {
                    navController.navigate(route = WeatherScreens.SearchScreen.name)
                },
                onButtonClicked = {
                    Log.d(TAG,"MainScaffold: Button Clicked")
                }
            )
        }
    ) {
        MainContent(weather, isImperial = isImperial)
    }
}

@Composable 
fun MainContent(data: Weather, isImperial: Boolean) {
    val weatherItem = data.list.first()
    val weatherImgUrl = "https://openweathermap.org/img/wn/${weatherItem.weather.first().icon}.png"
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatDate(weatherItem.dt),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )
        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                WeatherStateImage(weatherImgUrl)
                Text( //temperature
                    text = formatDecimals(weatherItem.temp.day) + "??",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold
                )
                Text( //description of weather
                    text = weatherItem.weather.first().main,
                    fontStyle = FontStyle.Italic
                )
            }
        }
        HumidityWindPressureRow(weather = weatherItem, isImperial = isImperial)
        Divider()
        SunsetSunriseRow(weather = weatherItem)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, bottom = 2.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = SUBTITLE,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.subtitle1
            )
        }
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFEEF1EF),
            shape = RoundedCornerShape(14.dp)
        ) {
            LazyColumn(
                modifier = Modifier.padding(2.dp),
                contentPadding = PaddingValues(1.dp) //apply to items inside column
            ) {
                items(items = data.list) { item: WeatherItem ->
                    WeatherDetailRow(weather = item)
                }
            }
        }
    }
}

