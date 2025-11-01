package com.hsappcreators.weather.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast_table")
data class ForecastEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val lat : Double?,
    val lon : Double?,
    val cityName: String?,
    val dateTime: Long?,          // Forecast time (epoch)
    val temperature: Double?,
    val maxTemp: Double?,
    val minTemp: Double?,
    val icon: String?
)

