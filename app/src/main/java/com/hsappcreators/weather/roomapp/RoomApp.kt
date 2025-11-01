package com.hsappcreators.weather.roomapp

import android.app.Application
import com.hsappcreators.weather.database.WeatherDatabase
import com.hsappcreators.weather.roomrepository.WeatherRoomRepository

class RoomApp : Application() {
    val database by lazy { WeatherDatabase.getInstance(this) }
    val repository by lazy { WeatherRoomRepository(database.weatherDao()) }
}