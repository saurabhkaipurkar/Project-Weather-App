package com.project.weather.apiservices

import com.project.weather.models.GeocodingResponse
import com.project.weather.models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("weather")
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String
    ): WeatherResponse

    @GET("weather")
    suspend fun getWeatherDataByCityName(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): WeatherResponse

    @GET("direct")
    suspend fun FetchStateName(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ): List<GeocodingResponse>
}