package com.example.mybestweatherapplicationv20.model

import com.google.gson.annotations.SerializedName

data class ForecastWeatherUI (
    val list: List<ListForecast>,
    val city: City
)

data class ListForecast(
    val main: MainForecast,
    val weather: List<WeatherForecast>,
    @SerializedName("dt_txt") val dtTxt: String
)

data class MainForecast(
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double,
)

data class WeatherForecast(
    val id: Int
)

data class City(
    val timezone: Int
)
