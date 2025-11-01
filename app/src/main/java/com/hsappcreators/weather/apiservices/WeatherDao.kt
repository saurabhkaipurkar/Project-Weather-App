package com.hsappcreators.weather.apiservices

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hsappcreators.weather.models.AqiEntity
import com.hsappcreators.weather.models.ForecastEntity
import com.hsappcreators.weather.models.WeatherEntity

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

    // AQI
    @Query("SELECT * FROM AQI_table ORDER BY id DESC LIMIT 1")
    fun getLatestAqi(): LiveData<AqiEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAqi(aqi: AqiEntity)
}
