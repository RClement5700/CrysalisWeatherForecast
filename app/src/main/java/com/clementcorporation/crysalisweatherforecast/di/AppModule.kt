package com.clementcorporation.crysalisweatherforecast.di

import android.content.Context
import androidx.room.Room
import com.clementcorporation.crysalisweatherforecast.data.WeatherDao
import com.clementcorporation.crysalisweatherforecast.data.WeatherDatabase
import com.clementcorporation.crysalisweatherforecast.network.WeatherApi
import com.clementcorporation.crysalisweatherforecast.repository.WeatherDbRepository
import com.clementcorporation.crysalisweatherforecast.repository.WeatherRepository
import com.clementcorporation.crysalisweatherforecast.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao = weatherDatabase.weatherDao()

    @Provides
    @Singleton
    fun providesWeatherDatabase(@ApplicationContext context: Context): WeatherDatabase {
        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "weather_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesWeatherRepository(weatherDao: WeatherDao): WeatherDbRepository {
        return WeatherDbRepository(weatherDao = weatherDao)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherApi): WeatherRepository {
        return WeatherRepository(api = api)
    }

    @Provides
    @Singleton
    fun provideOpenWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
}