package com.clementcorporation.crysalisweatherforecast.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.clementcorporation.crysalisweatherforecast.navigation.WeatherScreens

private val MENU_ITEMS = listOf("About", "Favorites", "Settings")
private const val MORE_ADA = "more icon"
private const val SEARCH_ADA = "search icon"
private const val PLACEHOLDER = "title"
@Composable
fun WeatherAppBar(
    title: String = PLACEHOLDER,
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
    ) {
    val showDialog = remember {
        mutableStateOf(false)
    }
    if (showDialog.value) {
        ShowSettingDropDownMenu(showDialog = showDialog, navController = navController)
    }
    TopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colors.onSecondary,
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        },
        actions = {
            if (isMainScreen) {
                IconButton(
                    onClick = {
                        onAddActionClicked.invoke()
                    }
                ) {
                    Icon(imageVector = Icons.Default.Search,
                        contentDescription = SEARCH_ADA
                    )
                }
                IconButton(
                    onClick = {
                    showDialog.value = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = MORE_ADA
                    )
                }
            } else Box {
            }
        },
        navigationIcon = {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSecondary,
                    modifier = Modifier.clickable {
                        onButtonClicked.invoke()
                    }
                )
            }
        },
        backgroundColor = Color.Transparent,
        elevation = elevation
    )
}

@Composable
fun ShowSettingDropDownMenu(showDialog: MutableState<Boolean>, navController: NavController) {
    var expanded by remember {mutableStateOf(true)}
    val items = MENU_ITEMS
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(
                top = 45.dp,
                right = 20.dp
            )
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false},
            modifier = Modifier
                .width(140.dp)
                .background(Color.White)
            ) {
            items.forEachIndexed { index, text ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        showDialog.value = false
                    }
                ) {
                    Icon(
                        imageVector = when(text) {
                            MENU_ITEMS[0] -> Icons.Default.Info
                            MENU_ITEMS[1] -> Icons.Default.Favorite
                            else -> Icons.Default.Settings
                        },
                        contentDescription = null,
                        tint = Color.LightGray
                    )
                    Text(
                        text = text,
                        color = Color.Black,
                        modifier = Modifier.clickable {
                            navController.navigate(
                                when(text) {
                                    MENU_ITEMS[0] -> WeatherScreens.AboutScreen.name
                                    MENU_ITEMS[1] -> WeatherScreens.FavoriteScreen.name
                                    else -> WeatherScreens.SettingsScreen.name
                                }
                            )
                        },
                        fontWeight = FontWeight.W300
                    )
                }
            }
        }
    }
}
