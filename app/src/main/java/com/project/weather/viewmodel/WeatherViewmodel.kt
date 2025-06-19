package com.project.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.weather.models.GeocodingResponse
import com.project.weather.models.WeatherResponse
import com.project.weather.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewmodel(private val repository: WeatherRepository) : ViewModel() {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _response = MutableLiveData<WeatherResponse>()
    val response: LiveData<WeatherResponse> = _response

    fun getWeatherData(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                val response = repository.getForcastData(lat, lon)
                _response.value = response
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    //========================================================================================

    private val _cityResponse = MutableLiveData<WeatherResponse>()
    val cityResponse: LiveData<WeatherResponse> = _cityResponse

    fun getCityWeatherData(cityName: String) {
        viewModelScope.launch {
            try {
                val response = repository.weatherFromCityName(cityName)
                _cityResponse.value = response
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    //==============================================================================================

    private val _stateName = MutableLiveData<List<GeocodingResponse>>()
    val stateName: LiveData<List<GeocodingResponse>> = _stateName

    fun fetchStateName(cityName: String) {
        viewModelScope.launch {
            try {
                val response = repository.getCityData(cityName)
                _stateName.value = response
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}