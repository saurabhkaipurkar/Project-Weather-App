package com.project.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.weather.models.WeatherResponse
import com.project.weather.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewmodel : ViewModel() {

    private val repository = WeatherRepository()

    private val _response = MutableLiveData<WeatherResponse>()
    val response: LiveData<WeatherResponse> = _response


    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error


    fun getWeatherData(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                val response = repository.getForcastData(lat, lon)
                _response.postValue(response)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }
    //=================================================================================

    private val _cityResponse = MutableLiveData<WeatherResponse>()
    val cityResponse: LiveData<WeatherResponse> = _cityResponse

    private val _cityError = MutableLiveData<String>()
    val cityError: LiveData<String> = _cityError

    fun getCityWeatherData(cityName: String) {
        viewModelScope.launch {
            try {
                val response = repository.weatherFromCityName(cityName)
                _cityResponse.postValue(response)
            } catch (e: Exception) {
                _cityError.postValue(e.message)
            }
        }
    }
}