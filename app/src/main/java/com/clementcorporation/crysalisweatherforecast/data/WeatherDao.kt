package com.clementcorporation.crysalisweatherforecast.data

import androidx.room.*
import com.clementcorporation.crysalisweatherforecast.model.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    //Favorites
    @Query(value = "SELECT * FROM favorites_table")
    fun getFavorites(): Flow<List<Favorite>>

    @Query("SELECT * FROM favorites_table WHERE city = :city")
    suspend fun getFavById(city: String): Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Query("DELETE FROM favorites_table")
    suspend fun deleteAllFavorites()

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    //CrysalisUnits
    @Query(value = "SELECT * FROM unit_table")
    fun getUnits(): Flow<List<CrysalisUnit>>

    @Query("SELECT * FROM unit_table WHERE unit = :unit")
    suspend fun getUnitById(unit: String): CrysalisUnit

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: CrysalisUnit)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(unit: CrysalisUnit)

    @Query("DELETE FROM unit_table")
    suspend fun deleteAllUnits()

    @Delete
    suspend fun deleteUnit(unit: CrysalisUnit)
}