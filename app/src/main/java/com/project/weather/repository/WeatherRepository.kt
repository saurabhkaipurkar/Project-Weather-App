package com.project.weather.repository

import com.project.weather.apiservices.ApiServices
import com.project.weather.models.GeocodingResponse
import com.project.weather.models.WeatherResponse

class WeatherRepository(private val apiService: ApiServices) {

    private val apikey = "8141f502ab11cace94aa0f8d5ab1c7ca"

    suspend fun getForcastData(lat: Double, lon: Double): WeatherResponse {
        return apiService.getWeatherData(lat, lon, apikey)
    }
    suspend fun weatherFromCityName(cityName: String): WeatherResponse{
        return apiService.getWeatherDataByCityName(cityName,apikey)
    }

    suspend fun getCityData(city: String): List<GeocodingResponse>{
        return apiService.fetchStateName(city, apikey)
    }
}