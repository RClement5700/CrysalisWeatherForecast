package com.clementcorporation.crysalisweatherforecast.repository

import com.clementcorporation.crysalisweatherforecast.data.CrysalisUnit
import com.clementcorporation.crysalisweatherforecast.data.WeatherDao
import com.clementcorporation.crysalisweatherforecast.model.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {

    //favorites
    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()
    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite = favorite)
    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite = favorite)
    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite = favorite)
    suspend fun deleteAllFavorites() = weatherDao.deleteAllFavorites()
    suspend fun getFavoriteByCity(city: String) = weatherDao.getFavById(city = city)


    //units
    fun getUnits(): Flow<List<CrysalisUnit>> = weatherDao.getUnits()
    suspend fun insertUnit(unit: CrysalisUnit) = weatherDao.insertUnit(unit = unit)
    suspend fun updateUnit(unit: CrysalisUnit) = weatherDao.updateUnit(unit)
    suspend fun deleteUnit(unit: CrysalisUnit) = weatherDao.deleteUnit(unit)
    suspend fun deleteAllUnits() = weatherDao.deleteAllUnits()
    suspend fun getUnitById(unit: String) = weatherDao.getUnitById(unit)
}