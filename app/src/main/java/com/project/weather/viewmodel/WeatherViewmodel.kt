package com.project.weather.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.weather.models.GeocodingResponse
import com.project.weather.models.OpenCageResponse
import com.project.weather.models.WeatherResponse
import com.project.weather.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewmodel(private val repository: WeatherRepository) : ViewModel() {

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
    //========================================================================================

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

    //============================================================================================

    private val _reverseResponse = MutableLiveData<OpenCageResponse>()
    val reverseResponse: LiveData<OpenCageResponse> = _reverseResponse

    private val _reverseError = MutableLiveData<String>()
    val reverseError: LiveData<String> = _reverseError

    fun getReverseApiData(location: String, context: Context) {
        viewModelScope.launch {
            try {
                val reverseResponse = repository.reverseApiCalling(location)
                _reverseResponse.postValue(reverseResponse)
                Toast.makeText(context, reverseResponse.toString(), Toast.LENGTH_SHORT).show()
            }catch (e: Exception){
                _reverseError.postValue(e.message)
            }
        }
    }
    //==============================================================================================

    private val _stateName = MutableLiveData<List<GeocodingResponse>>()
    val stateName: LiveData<List<GeocodingResponse>> = _stateName

    private val _stateNameError = MutableLiveData<String>()
    val stateNameError: LiveData<String> = _stateNameError

    fun fetchStateName(cityName: String) {
        viewModelScope.launch {
            try {
                val response = repository.getCityData(cityName)
                _stateName.postValue(response)
            } catch (e: Exception) {
                _stateNameError.postValue(e.message)
            }
        }
    }
}