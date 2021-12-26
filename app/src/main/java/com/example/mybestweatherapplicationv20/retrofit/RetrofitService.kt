package com.example.mybestweatherapplicationv20.retrofit

import com.example.mybestweatherapplicationv20.model.CurrentWeatherUI
import com.example.mybestweatherapplicationv20.model.ForecastWeatherUI
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface RetrofitService {
    @GET
    suspend fun getCurrentWeather(@Url url: String):Response<CurrentWeatherUI>

    @GET// "forecast?q=Томск&appid=90be23a3844509ed447fa2a2cf63adb9&units=metric&lang=ru&cnt=6"
    suspend fun getForecastWeather(@Url url: String):Response<ForecastWeatherUI>

}