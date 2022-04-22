package com.clementcorporation.crysalisweatherforecast.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.clementcorporation.crysalisweatherforecast.data.CrysalisUnit
import com.clementcorporation.crysalisweatherforecast.widgets.WeatherAppBar

private const val TITLE = "Settings"
private const val SUBTITLE = "Change Units of Measurements"
private const val BUTTON_TEXT = "Save"
private const val CELSIUS = "Celsius (ºC)"
private const val FAHRENHEIT = "Fahrenheit (ºF)"
@Composable
fun SettingsScreen(navController: NavController, settingsViewModel: SettingsViewModel = hiltViewModel()) {

    val choiceFromDb = settingsViewModel.unitList.collectAsState().value
    val measurementUnits = listOf("Imperial (F)","Metric (C)")
    val defaultChoice = if (choiceFromDb.isEmpty()) measurementUnits.first() else choiceFromDb.first().unit
    var unitToggleState by remember {
        mutableStateOf(false)
    }
    var choiceState by remember {
        mutableStateOf(defaultChoice)
    }

    Scaffold(
        backgroundColor = Color.White,
        topBar = {
            WeatherAppBar(
                title = TITLE,
                icon = Icons.Default.ArrowBack,
                isMainScreen = false,
                navController = navController
            ) {
                navController.popBackStack()
            }
        }
    ) {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = CenterHorizontally) {
                Text(
                    text = SUBTITLE,
                    modifier = Modifier.padding(bottom = 15.dp)
                )
                IconToggleButton(
                    checked = !unitToggleState,
                    onCheckedChange = {
                        unitToggleState = !it
                        choiceState = if (unitToggleState) measurementUnits.first() else measurementUnits[1]
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clip(shape = RectangleShape)
                        .padding(5.dp)
                        .background(Color.Magenta)
                ) {
                    Text(text = if (unitToggleState) FAHRENHEIT else CELSIUS)
                }
                Button(
                    modifier = Modifier
                        .padding(3.dp)
                        .align(CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFEFBE42)
                    ),
                    onClick = {
                        settingsViewModel.deleteAllUnits()
                        settingsViewModel.insertUnit(CrysalisUnit(choiceState))
                    }
                ) {
                    Text(
                        text = BUTTON_TEXT,
                        modifier = Modifier.padding(4.dp),
                        color = Color.White,
                        fontSize = 17.sp
                    )
                }
            }
        }
    }
}