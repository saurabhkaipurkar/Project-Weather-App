package com.saurabh.weather.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.saurabh.weather.R
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

    fun getAQIDescription(aqi: Int): String {
        return when (aqi) {
            1 -> "Good"
            2 -> "Fair"
            3 -> "Moderate"
            4 -> "Poor"
            5 -> "Very Poor"
            else -> "Unknown"
        }
    }

    fun getWeatherIcon(weatherCondition: String): Int {
        return when (weatherCondition) {
            "Clouds" -> R.drawable.clouds
            "Clear" -> R.drawable.sun
            "Rain" -> R.drawable.rain
            "Snow" -> R.drawable.snow
            "Thunderstorm" -> R.drawable.thunderstorm
            else -> R.drawable.sun
        }
    }

    fun isInternetAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val activeNetwork = cm.getNetworkCapabilities(network) ?: return false
        return activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) ||
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
    }
}