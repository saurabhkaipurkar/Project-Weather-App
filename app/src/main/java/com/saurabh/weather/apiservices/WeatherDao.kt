package com.saurabh.weather.apiservices

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saurabh.weather.models.ForecastEntity
import com.saurabh.weather.models.WeatherEntity

@Dao
interface WeatherDao {

    // Current Weather
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)

    @Query("SELECT * FROM weather_table ORDER BY dateTime DESC LIMIT 1")
    fun getLatestWeather(): LiveData<WeatherEntity>


    // Forecast
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(forecast: List<ForecastEntity>)

    @Query("SELECT * FROM forecast_table ORDER BY dateTime ASC")
    fun getForecastForCity(): LiveData<List<ForecastEntity>>
}
