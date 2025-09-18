package com.saurabh.weather.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.saurabh.weather.R
import com.saurabh.weather.databinding.HourlyForecastBinding
import com.saurabh.weather.models.ForecastEntity
import com.saurabh.weather.utils.MethodLibrary

class WeatherRoomForecastAdapter(private val data: List<ForecastEntity>
) : RecyclerView.Adapter<WeatherRoomForecastAdapter.WeatherRoomForecastViewHolder>() {

    private val toolBox = MethodLibrary()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherRoomForecastViewHolder {
       return WeatherRoomForecastViewHolder(HourlyForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(
        holder: WeatherRoomForecastViewHolder,
        position: Int
    ) {
        val forecastItem = data[position]
        holder.bind(forecastItem)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    inner class WeatherRoomForecastViewHolder(private val binding : HourlyForecastBinding) : RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(forecastItem: ForecastEntity) {
            binding.dayOfWeek.text = toolBox.timeStampConvertor(forecastItem.dateTime!!)
            binding.highTemp.text = "${toolBox.kelvinToCelsius(forecastItem.maxTemp!!)} °C"
            binding.lowTemp.text = "${toolBox.kelvinToCelsius(forecastItem.minTemp!!)} °C"

            val weatherIcon = when (forecastItem.icon) {
                "Clouds" -> R.drawable.clouds
                "Clear" -> R.drawable.sun
                "Rain" -> R.drawable.rain
                "Snow" -> R.drawable.snow
                "Thunderstorm" -> R.drawable.thunderstorm
                else -> R.drawable.sun
            }
            binding.weatherIcon.setImageResource(weatherIcon)
        }
    }
}