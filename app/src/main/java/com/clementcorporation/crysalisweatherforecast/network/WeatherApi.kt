package com.clementcorporation.crysalisweatherforecast.network

import com.clementcorporation.crysalisweatherforecast.model.Weather
import com.clementcorporation.crysalisweatherforecast.util.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") query: String = "los+angeles",
        @Query("units") units: String = "imperial",
        @Query("appid") appId: String = API_KEY
    ): Weather
}