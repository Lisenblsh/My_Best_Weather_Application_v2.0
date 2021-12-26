package com.example.mybestweatherapplicationv20.repository

import com.example.mybestweatherapplicationv20.retrofit.Constrains
import com.example.mybestweatherapplicationv20.retrofit.RetrofitService
import javax.inject.Inject

class WeatherRepository
@Inject
constructor(private val retrofitService: RetrofitService) {
    suspend fun getCurrentWeather() = retrofitService.getCurrentWeather("weather?q=${Constrains.city}&appid=90be23a3844509ed447fa2a2cf63adb9&units=metric&lang=ru")

    suspend fun getForecastWeather() = retrofitService.getForecastWeather("forecast?q=${Constrains.city}&appid=90be23a3844509ed447fa2a2cf63adb9&units=metric&lang=ru&cnt=6")
}