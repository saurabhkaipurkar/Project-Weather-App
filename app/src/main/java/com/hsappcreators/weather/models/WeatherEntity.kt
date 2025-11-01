package com.hsappcreators.weather.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val cityName: String?,
    val stateName: String?,
    val temperature: Double?,
    val description: String?,
    val humidity: Int?,
    val windSpeed: Double?,
    val pressure: Int?,
    val dateTime: Long?,
    val clouds: Int?,
    val visibility: Int?,
    val maxTemp: Double?,
    val minTemp: Double?,
    val feelsLike: Double?,
    val icon: String?,
)
