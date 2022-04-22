package com.clementcorporation.crysalisweatherforecast.screens.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clementcorporation.crysalisweatherforecast.model.Favorite
import com.clementcorporation.crysalisweatherforecast.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "Favorites: "
@HiltViewModel
class FavoritesViewModel @Inject constructor(private val weatherDbRepository: WeatherDbRepository): 
    ViewModel() {
    private val _favList = MutableStateFlow<List<Favorite>>(emptyList())
    val favList = _favList.asStateFlow() //READ-ONLY state flow

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getFavorites().distinctUntilChanged() //removes repetition
                .collect { listOfFavorites ->
                    if (listOfFavorites.isNullOrEmpty()) {
                        Log.d(TAG, "No Favorites Listed")
                    } else {
                        _favList.value = listOfFavorites
                        favList.value.forEach {
                            Log.d(TAG, it.city)
                        }
                    }
                }
        }
    }

    private fun getFavorites(): Flow<List<Favorite>> = weatherDbRepository.getFavorites()
    fun insertFavorite(favorite: Favorite) = viewModelScope.launch {
        weatherDbRepository.insertFavorite(favorite = favorite)
    }
    fun updateFavorite(favorite: Favorite) = viewModelScope.launch {
        weatherDbRepository.updateFavorite(favorite = favorite)
    }
    fun deleteFavorite(favorite: Favorite) = viewModelScope.launch {
        weatherDbRepository.deleteFavorite(favorite = favorite)
    }
    fun deleteAllFavorites() = viewModelScope.launch {
        weatherDbRepository.deleteAllFavorites()
    }
    fun getFavoriteByCity(city: String) = viewModelScope.launch {
        weatherDbRepository.getFavoriteByCity(city = city)
    }
}