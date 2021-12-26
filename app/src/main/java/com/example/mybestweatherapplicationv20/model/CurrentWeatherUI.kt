package com.example.mybestweatherapplicationv20.model

import com.google.gson.annotations.SerializedName


data class CurrentWeatherUI(
    val timezone: Int,
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind,
    val dt: Long,
    val sys: Sys,
    val name: String
)

data class Weather(
    val id: Int,
    val description: String,
)

data class Main(
    val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double,
    val pressure: Int,
    val humidity: Int
)

data class Wind(
    val speed: Double,
    val deg: Int
)


data class Sys(
    val sunrise: Long,
    val sunset: Long
)
