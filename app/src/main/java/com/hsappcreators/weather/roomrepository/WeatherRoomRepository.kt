package com.hsappcreators.weather.roomrepository

import androidx.lifecycle.LiveData
import com.hsappcreators.weather.apiservices.WeatherDao
import com.hsappcreators.weather.models.AqiEntity
import com.hsappcreators.weather.models.ForecastEntity
import com.hsappcreators.weather.models.WeatherEntity

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

    suspend fun insertAqi(aqi: AqiEntity) {
        dao.insertAqi(aqi)
    }

    val latestAqi: LiveData<AqiEntity> = dao.getLatestAqi()
}
