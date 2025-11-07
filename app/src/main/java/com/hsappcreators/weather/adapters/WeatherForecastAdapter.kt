package com.hsappcreators.weather.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.hsappcreators.weather.utils.MethodLibrary
import com.hsappcreators.weather.R
import com.hsappcreators.weather.databinding.HourlyForecastBinding
import com.hsappcreators.weather.models.ForecastItem
import com.hsappcreators.weather.models.ForecastResponse

class WeatherForecastAdapter(private val data: ForecastResponse,
                             private val toolBox : MethodLibrary
) : RecyclerView.Adapter<WeatherForecastAdapter.WeatherForecastViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherForecastViewHolder {
        return WeatherForecastViewHolder(HourlyForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(
        holder: WeatherForecastViewHolder,
        position: Int
    ) {
        val forecastItem = data.list[position]
        holder.bind(forecastItem)
        // Set time from timestamp
    }

    override fun getItemCount(): Int {
        return data.list.size
    }

    inner class WeatherForecastViewHolder(val binding : HourlyForecastBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : ForecastItem) = with(binding) {

            dayOfWeek.text = toolBox.timeStampConvertor(item.dt)

            // Set temperatures
            highTemp.text = "${toolBox.kelvinToCelsius(item.main.tempMax)} °C"
            lowTemp.text = "${toolBox.kelvinToCelsius(item.main.tempMin)} °C"

            // Set weather icon based on condition
            val weatherImage = when (item.weather[0].main) {
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