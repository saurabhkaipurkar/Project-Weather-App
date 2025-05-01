package com.project.weather.repository

import com.project.weather.apiservices.RetrofitInstance
import com.project.weather.models.WeatherResponse

class WeatherRepository {

    private val apikey = "8141f502ab11cace94aa0f8d5ab1c7ca"

    suspend fun getForcastData(lat: Double, lon: Double): WeatherResponse {

        return RetrofitInstance.retrofit.getWeatherData(lat,lon,apikey)
    }
    suspend fun weatherFromCityName(cityName: String): WeatherResponse{
        return RetrofitInstance.retrofit.getWeatherDataByCityName(cityName,apikey)
    }
}