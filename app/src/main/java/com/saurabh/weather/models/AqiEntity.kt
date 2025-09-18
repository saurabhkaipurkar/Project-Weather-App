package com.saurabh.weather.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AQI_table")
data class AqiEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val aqi: String?,
)
