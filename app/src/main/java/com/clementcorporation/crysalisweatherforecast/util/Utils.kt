package com.clementcorporation.crysalisweatherforecast.util

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(timestamp: Int): String {
    val sdf = SimpleDateFormat("EEE, MMM d", Locale.US)
    val date = java.util.Date(timestamp.toLong() * 1000)

    return sdf.format(date)
}

fun formatTime(timestamp: Int): String {
    val sdf = SimpleDateFormat.getTimeInstance()
    val date = java.util.Date(timestamp.toLong() * 1000)
    return sdf.format(date)
}

fun formatDateTime(timestamp: Int): String {
    val sdf = SimpleDateFormat.getDateTimeInstance()
    val date = java.util.Date(timestamp.toLong() * 1000)

    return sdf.format(date)
}

fun formatDecimals(item: Double): String {
    return " %.0f".format(item)
}