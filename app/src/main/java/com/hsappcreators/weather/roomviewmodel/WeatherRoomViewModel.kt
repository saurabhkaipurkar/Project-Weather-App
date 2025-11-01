package com.hsappcreators.weather.roomviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hsappcreators.weather.models.AqiEntity
import com.hsappcreators.weather.models.ForecastEntity
import com.hsappcreators.weather.models.WeatherEntity
import com.hsappcreators.weather.roomrepository.WeatherRoomRepository
import kotlinx.coroutines.launch

class WeatherRoomViewModel(private val repository: WeatherRoomRepository) : ViewModel() {

    val latestWeather: LiveData<WeatherEntity> = repository.latestWeather

    fun insertWeather(weather: WeatherEntity) = viewModelScope.launch {
        repository.insertWeather(weather)
    }

    fun insertForecast(forecasts: List<ForecastEntity>) = viewModelScope.launch {
        repository.insertForecast(forecasts)
    }

    fun getForecast(): LiveData<List<ForecastEntity>> {
        return repository.getForecast()
    }

    fun insertAqi(aqi: AqiEntity) = viewModelScope.launch {
        repository.insertAqi(aqi)
    }

    val latestAqi: LiveData<AqiEntity> = repository.latestAqi
}
