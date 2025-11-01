package com.hsappcreators.weather.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.hsappcreators.weather.R
import com.hsappcreators.weather.databinding.HourlyForecastBinding
import com.hsappcreators.weather.models.ForecastEntity
import com.hsappcreators.weather.utils.MethodLibrary

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