package com.saurabh.weather.apiservices

import com.saurabh.weather.models.AirQualityResponse
import com.saurabh.weather.models.ForecastResponse
import com.saurabh.weather.models.GeocodingResponse
import com.saurabh.weather.models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("data/2.5/weather")
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String
    ): WeatherResponse

    @GET("data/2.5/weather")
    suspend fun getWeatherDataByCityName(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): WeatherResponse

    @GET("geo/1.0/direct")
    suspend fun fetchStateName(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ) : List<GeocodingResponse>

    @GET("data/2.5/forecast")
    suspend fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String
    ) : ForecastResponse

    @GET("data/2.5/air_pollution")
    suspend fun getAirQuality(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String
    ) : AirQualityResponse
}