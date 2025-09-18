package com.saurabh.weather.roomviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.saurabh.weather.roomrepository.WeatherRoomRepository

class WeatherRoomViewModelFactory(private val repository: WeatherRoomRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherRoomViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeatherRoomViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}