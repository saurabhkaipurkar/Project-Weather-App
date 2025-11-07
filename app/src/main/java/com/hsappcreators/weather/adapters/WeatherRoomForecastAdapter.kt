package com.hsappcreators.weather.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.hsappcreators.weather.R
import com.hsappcreators.weather.databinding.HourlyForecastBinding
import com.hsappcreators.weather.models.ForecastEntity
import com.hsappcreators.weather.utils.MethodLibrary

class WeatherRoomForecastAdapter(
    private val data: List<ForecastEntity>,
    private val toolBox: MethodLibrary
) : RecyclerView.Adapter<WeatherRoomForecastAdapter.WeatherRoomForecastViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherRoomForecastViewHolder {
        return WeatherRoomForecastViewHolder(
            HourlyForecastBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
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


    inner class WeatherRoomForecastViewHolder(private val binding: HourlyForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(forecastItem: ForecastEntity) = with(binding) {
            dayOfWeek.text = toolBox.timeStampConvertor(forecastItem.dateTime!!)
            highTemp.text = "${toolBox.kelvinToCelsius(forecastItem.maxTemp!!)} °C"
            lowTemp.text = "${toolBox.kelvinToCelsius(forecastItem.minTemp!!)} °C"

            val weatherImage = when (forecastItem.icon) {
                "Clouds" -> R.drawable.clouds
                "Clear" -> R.drawable.sun
                "Rain" -> R.drawable.rain
                "Snow" -> R.drawable.snow
                "Thunderstorm" -> R.drawable.thunderstorm
                else -> R.drawable.sun
            }
            weatherIcon.setImageResource(weatherImage)
        }
    }
}