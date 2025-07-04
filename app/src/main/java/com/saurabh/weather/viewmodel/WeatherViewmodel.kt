package com.saurabh.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saurabh.weather.models.AirQualityResponse
import com.saurabh.weather.models.ForecastResponse
import com.saurabh.weather.models.GeocodingResponse
import com.saurabh.weather.models.WeatherResponse
import com.saurabh.weather.repository.WeatherRepository
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

    //==============================================================================================

    private val _forecast = MutableLiveData<ForecastResponse>()
    val forecast: LiveData<ForecastResponse> = _forecast

    fun fetchForecast(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                val response = repository.getForecastData(lat, lon)
                _forecast.value = response
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    //==============================================================================================

    private val _airQuality = MutableLiveData<AirQualityResponse>()
    val airQuality: LiveData<AirQualityResponse> = _airQuality

    fun fetchAirQuality(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                val response = repository.getAirQuality(lat, lon)
                _airQuality.value = response
                } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}