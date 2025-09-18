package com.saurabh.weather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.saurabh.weather.repository.WeatherRepository

class WeatherViewmodelFactory(private val repository: WeatherRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(WeatherViewmodel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return WeatherViewmodel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}