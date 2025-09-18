package com.saurabh.weather.roomrepository

import androidx.lifecycle.LiveData
import com.saurabh.weather.apiservices.WeatherDao
import com.saurabh.weather.models.ForecastEntity
import com.saurabh.weather.models.WeatherEntity

class WeatherRoomRepository(private val dao: WeatherDao) {

    val latestWeather: LiveData<WeatherEntity> = dao.getLatestWeather()

    suspend fun insertWeather(weather: WeatherEntity) {
        dao.insertWeather(weather)
    }

    suspend fun insertForecast(forecasts: List<ForecastEntity>) {
        dao.insertForecast(forecasts)
    }

    fun getForecast(): LiveData<List<ForecastEntity>> {
        return dao.getForecastForCity()
    }
}
