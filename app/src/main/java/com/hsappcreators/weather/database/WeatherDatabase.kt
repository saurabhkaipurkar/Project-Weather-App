package com.hsappcreators.weather.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hsappcreators.weather.apiservices.WeatherDao
import com.hsappcreators.weather.models.AqiEntity
import com.hsappcreators.weather.models.ForecastEntity
import com.hsappcreators.weather.models.WeatherEntity

@Database(
    entities = [WeatherEntity::class, ForecastEntity::class, AqiEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile private var instance: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase {
            return instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "weather_database"
                ).build()
                this.instance = instance
                instance
            }
        }
    }
}
