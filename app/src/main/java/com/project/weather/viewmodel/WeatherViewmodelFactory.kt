package com.project.weather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.weather.repository.WeatherRepository

class WeatherViewmodelFactory(private val repository: WeatherRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(WeatherViewmodel::class.java)){
            return WeatherViewmodel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}