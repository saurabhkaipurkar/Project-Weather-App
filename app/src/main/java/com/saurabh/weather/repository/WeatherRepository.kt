package com.saurabh.weather.repository

import com.saurabh.weather.apiservices.ApiServices
import com.saurabh.weather.models.ForecastResponse
import com.saurabh.weather.models.GeocodingResponse
import com.saurabh.weather.models.WeatherResponse

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

    suspend fun getForecastData(lat: Double, lon: Double): ForecastResponse {
        return apiService.getForecast(lat, lon, apikey)
    }
}