package com.hsappcreators.weather.repository

import com.hsappcreators.weather.apiservices.ApiServices
import com.hsappcreators.weather.models.AirQualityResponse
import com.hsappcreators.weather.models.ForecastResponse
import com.hsappcreators.weather.models.GeocodingResponse
import com.hsappcreators.weather.models.WeatherResponse

class WeatherRepository(private val apiService: ApiServices) {

    private val apikey = "8141f502ab11cace94aa0f8d5ab1c7ca"

    suspend fun getCurrentData(lat: Double, lon: Double): WeatherResponse {
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
    suspend fun getAirQuality(lat: Double, lon: Double): AirQualityResponse {
        return apiService.getAirQuality(lat, lon, apikey)
    }
}