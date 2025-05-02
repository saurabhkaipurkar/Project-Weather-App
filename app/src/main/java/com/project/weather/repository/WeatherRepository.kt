package com.project.weather.repository

import com.project.weather.apiservices.RetrofitInstance
import com.project.weather.models.GeocodingResponse
import com.project.weather.models.OpenCageResponse
import com.project.weather.models.WeatherResponse

class WeatherRepository {

    private val apikey = "8141f502ab11cace94aa0f8d5ab1c7ca"

    private val reverseApikey = "80efe688532b4d1ea01a2441bb41e7aa"

    suspend fun getForcastData(lat: Double, lon: Double): WeatherResponse {

        return RetrofitInstance.retrofit.getWeatherData(lat,lon,apikey)
    }
    suspend fun weatherFromCityName(cityName: String): WeatherResponse{
        return RetrofitInstance.retrofit.getWeatherDataByCityName(cityName,apikey)
    }

    suspend fun reverseApiCalling(location: String): OpenCageResponse{
        return RetrofitInstance.reverseApi.getAccurateLocation(location, reverseApikey)
    }

    suspend fun getCityData(city: String): List<GeocodingResponse>{
        return RetrofitInstance.geocoding.FetchStateName(city,apikey)
    }
}