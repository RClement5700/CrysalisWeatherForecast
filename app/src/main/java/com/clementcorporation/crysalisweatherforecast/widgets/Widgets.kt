package com.clementcorporation.crysalisweatherforecast.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.clementcorporation.crysalisweatherforecast.R
import com.clementcorporation.crysalisweatherforecast.model.WeatherItem
import com.clementcorporation.crysalisweatherforecast.util.formatDate
import com.clementcorporation.crysalisweatherforecast.util.formatDecimals
import com.clementcorporation.crysalisweatherforecast.util.formatTime

@Composable
fun WeatherDetailRow(weather: WeatherItem) {
    val weatherImgUrl = "https://openweathermap.org/img/wn/${weather.weather.first().icon}.png"
    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(//Day of Week; 3 char format
                text = formatDate(weather.dt).split(",").first(),
                modifier = Modifier.padding(start = 8.dp)
            )
            WeatherStateImage(weatherImgUrl = weatherImgUrl)
            Surface(
                modifier = Modifier.padding(0.dp),
                shape = CircleShape,
                color = Color(0xFFFFC400)
            ) {
                Text(
                    text = weather.weather.first().description,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.caption
                )
            }
            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Blue.copy(alpha = 0.7f),
                            fontWeight = FontWeight.SemiBold
                        )
                    ) {
                        append(formatDecimals(weather.temp.max) + "º")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color.LightGray,
                        )
                    ) {
                        append(formatDecimals(weather.temp.min) + "º")
                    }
                }
            )
        }
    }
}

@Composable
fun SunsetSunriseRow(weather: WeatherItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, bottom = 6.dp, start = 4.dp, end = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Text(
                text = formatTime(weather.sunrise),
                style = MaterialTheme.typography.caption
            )
            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "sunrise"
            )
        }

        Row {
            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "sunset"
            )
            Text(
                text = formatTime(weather.sunset),
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem, isImperial: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity icon"
            )
            Text(
                text = "${weather.humidity}%",
                style = MaterialTheme.typography.caption
            )
        }

        Row(
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure icon"
            )
            Text(
                text = "${weather.pressure} psi",
                style = MaterialTheme.typography.caption
            )
        }

        Row(
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "wind icon"
            )
            Text(
                text = formatDecimals(weather.speed) + if (isImperial) "mph" else "m/s",
                style = MaterialTheme.typography.caption
            )
        }
    }
}


@Composable
fun WeatherStateImage(weatherImgUrl: String) {
    Image(
        modifier = Modifier.size(80.dp),
        painter = rememberImagePainter(weatherImgUrl),
        contentDescription = "icon image"
    )
}