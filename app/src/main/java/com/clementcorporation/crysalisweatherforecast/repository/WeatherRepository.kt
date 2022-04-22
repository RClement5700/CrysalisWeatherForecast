package com.clementcorporation.crysalisweatherforecast.repository

import android.util.Log
import com.clementcorporation.crysalisweatherforecast.data.DataOrException
import com.clementcorporation.crysalisweatherforecast.model.Weather
import com.clementcorporation.crysalisweatherforecast.model.WeatherObject
import com.clementcorporation.crysalisweatherforecast.network.WeatherApi
import javax.inject.Inject

private const val TAG = "WeatherRepo"
class WeatherRepository @Inject constructor(private val api: WeatherApi) {
    suspend fun getWeather(cityQuery: String, units: String): DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(query = cityQuery, units = units)
        } catch(e: Exception) {
            Log.d(TAG, "getWeather: ${e.localizedMessage}")
            return DataOrException(e = e)
        }
        Log.d(TAG, "getWeather: ${response.city.country}")
        return DataOrException(data = response)
    }
}