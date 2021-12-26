package com.example.mybestweatherapplicationv20.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybestweatherapplicationv20.model.CurrentWeatherUI
import com.example.mybestweatherapplicationv20.model.ForecastWeatherUI
import com.example.mybestweatherapplicationv20.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel
@Inject
constructor(private val weatherRepo: WeatherRepository) : ViewModel() {
    private val _currentWeather = MutableLiveData<CurrentWeatherUI>()
    val currentWeatherResp: LiveData<CurrentWeatherUI>
        get() = _currentWeather
    private val _forecastWeather = MutableLiveData<ForecastWeatherUI>()
    val forecastWeatherResp: LiveData<ForecastWeatherUI>
        get() = _forecastWeather

    init {
        getCurrentWeather()
        getForecastWeather()
    }

    fun updateData() = viewModelScope.launch {
        getCurrentWeather()
        getForecastWeather()
    }

    private fun getCurrentWeather() = viewModelScope.launch {
        weatherRepo.getCurrentWeather().let { response ->
            if (response.isSuccessful) {
                _currentWeather.postValue(response.body())
            } else {
                Log.d("Tab", "getWeather Error Response: ${response.message()}")
            }
        }
    }

    private fun getForecastWeather() = viewModelScope.launch {
        weatherRepo.getForecastWeather().let { response ->
            if (response.isSuccessful) {
                _forecastWeather.postValue(response.body())
            } else {
                Log.d("Tab", "getWeather Error Response: ${response.message()}")
            }
        }

    }
}