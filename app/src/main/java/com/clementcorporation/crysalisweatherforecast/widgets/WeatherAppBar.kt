package com.clementcorporation.crysalisweatherforecast.widgets

import android.content.Context
import android.widget.Toast
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.clementcorporation.crysalisweatherforecast.model.Favorite
import com.clementcorporation.crysalisweatherforecast.navigation.WeatherScreens
import com.clementcorporation.crysalisweatherforecast.screens.favorites.FavoritesViewModel

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
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
    navController: NavController,
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
    ) {
    val showIt = remember {
        mutableStateOf(false)
    }
    val showDialog = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
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
            if(isMainScreen) {
                val listOfFavorites = favoritesViewModel.favList.collectAsState().value.filter{item ->
                    item.city == title.split(",").first()
                }
                if (listOfFavorites.isEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "favorite icon",
                        tint = Color.Red,
                        modifier = Modifier
                            .scale(0.9f)
                            .clickable {
                                val cityCountry = title.split(",")
                                favoritesViewModel.insertFavorite(
                                    Favorite(
                                        city = cityCountry.first(),
                                        country = cityCountry[1]
                                    )
                                ).run {
                                    showIt.value = true
                                }
                            }
                    )
                } else {
                    showIt.value = false
                }
                ShowToast(context = context, showIt, title)
            }
        },
        backgroundColor = Color.Transparent,
        elevation = elevation
    )
}

@Composable
fun ShowToast(context: Context, showIt: MutableState<Boolean>, city: String) {
    if (showIt.value) {
        Toast.makeText(context, "$city added to Favorites", Toast.LENGTH_SHORT).show()
    }
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
