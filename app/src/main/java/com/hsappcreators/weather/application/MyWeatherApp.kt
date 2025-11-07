package com.hsappcreators.weather.application

import android.app.Application
import com.hsappcreators.weather.database.WeatherDatabase
import com.hsappcreators.weather.roomrepository.WeatherRoomRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyWeatherApp : Application() {
    val database by lazy { WeatherDatabase.getInstance(this) }
    val repository by lazy { WeatherRoomRepository(database.weatherDao()) }
}