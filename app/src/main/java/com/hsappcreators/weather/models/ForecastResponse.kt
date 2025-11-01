package com.hsappcreators.weather.models

import com.google.gson.annotations.SerializedName
data class ForecastResponse(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<ForecastItem>,
    val city: ForecastCity
)

data class ForecastItem(
    val dt: Long,
    val main: ForecastMain,
    val weather: List<Weather>,      // Can reuse Weather
    val clouds: Clouds,              // Can reuse Clouds
    val wind: Wind,                  // Can reuse Wind
    val visibility: Int,
    val pop: Double,
    val rain: Rain? = null,
    val sys: ForecastSys,
    @SerializedName("dt_txt") val dtTxt: String
)

data class ForecastMain(
    val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double,
    val pressure: Int,
    @SerializedName("sea_level") val seaLevel: Int,
    @SerializedName("grnd_level") val groundLevel: Int,
    val humidity: Int,
    @SerializedName("temp_kf") val tempKf: Double
)

data class Rain(
    @SerializedName("3h") val volume: Double
)

data class ForecastSys(
    val pod: String
)

data class ForecastCity(
    val id: Int,
    val name: String,
    val coord: Coord,          // Can reuse Coord
    val country: String,
    val population: Int,
    val timezone: Int,
    val sunrise: Long,
    val sunset: Long
)

