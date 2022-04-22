package com.clementcorporation.crysalisweatherforecast.screens.main

import androidx.lifecycle.ViewModel
import com.clementcorporation.crysalisweatherforecast.data.DataOrException
import com.clementcorporation.crysalisweatherforecast.model.Weather
import com.clementcorporation.crysalisweatherforecast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository): ViewModel() {

    suspend fun getWeatherData(city: String, units: String): DataOrException<Weather, Boolean, Exception> {
        return repository.getWeather(cityQuery = city, units = units)
    }
}