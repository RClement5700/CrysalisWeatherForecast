package com.clementcorporation.crysalisweatherforecast.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clementcorporation.crysalisweatherforecast.data.CrysalisUnit
import com.clementcorporation.crysalisweatherforecast.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SettingsViewModel: "
@HiltViewModel
class SettingsViewModel @Inject constructor(private val weatherDbRepository: WeatherDbRepository): ViewModel() {
    
    private val _unitList = MutableStateFlow<List<CrysalisUnit>>(emptyList())
    val unitList = _unitList.asStateFlow()
    
    init {
        viewModelScope.launch(Dispatchers.IO) {
            weatherDbRepository.getUnits().distinctUntilChanged().collect{ listOfUnits ->
                if (listOfUnits.isNullOrEmpty()) {
                    Log.d(TAG, "No Settings")
                } else {
                    _unitList.value = listOfUnits
                    unitList.value.forEach {
                        Log.d(TAG, it.unit)
                    }
                }
            }
        }
    }
    
    fun getUnits(): Flow<List<CrysalisUnit>> = weatherDbRepository.getUnits()
    fun insertUnit(unit: CrysalisUnit) = viewModelScope.launch { weatherDbRepository.insertUnit(unit = unit) }
    fun updateUnit(unit: CrysalisUnit) = viewModelScope.launch { weatherDbRepository.updateUnit(unit)}
    fun deleteUnit(unit: CrysalisUnit) = viewModelScope.launch { weatherDbRepository.deleteUnit(unit)}
    fun deleteAllUnits() = viewModelScope.launch { weatherDbRepository.deleteAllUnits()}
    fun getUnitById(unit: String) = viewModelScope.launch { weatherDbRepository.getUnitById(unit)}
}