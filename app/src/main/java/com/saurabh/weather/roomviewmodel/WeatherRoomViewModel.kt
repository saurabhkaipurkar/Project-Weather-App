package com.saurabh.weather.roomviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.saurabh.weather.models.ForecastEntity
import com.saurabh.weather.models.WeatherEntity
import com.saurabh.weather.roomrepository.WeatherRoomRepository
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
}
