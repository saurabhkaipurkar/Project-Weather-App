package com.saurabh.weather.roomapp

import android.app.Application
import com.saurabh.weather.database.WeatherDatabase
import com.saurabh.weather.roomrepository.WeatherRoomRepository

class RoomApp : Application() {
    val database by lazy { WeatherDatabase.getInstance(this) }
    val repository by lazy { WeatherRoomRepository(database.weatherDao()) }
}