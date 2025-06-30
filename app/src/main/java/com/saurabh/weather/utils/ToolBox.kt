package com.saurabh.weather.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class MethodLibrary {
    @RequiresApi(Build.VERSION_CODES.O)
    fun timeStampConvertor(timeStamp: Long): String {
        val sourceZone = ZoneId.of("UTC")
        val targetZone = ZoneId.of("Asia/Kolkata")
        val sourceTimeStamp = Instant.ofEpochSecond(timeStamp).atZone(sourceZone)
        val targetTimeStamp = sourceTimeStamp.withZoneSameInstant(targetZone)
        return targetTimeStamp.format(DateTimeFormatter.ofPattern("HH:mm"))
    }
    fun kelvinToCelsius(kelvin: Double): Int {
        return (kelvin - 273.15).toInt()
    }
}