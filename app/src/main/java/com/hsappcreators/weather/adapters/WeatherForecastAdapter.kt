package com.hsappcreators.weather.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.hsappcreators.weather.utils.MethodLibrary
import com.hsappcreators.weather.R
import com.hsappcreators.weather.models.ForecastResponse

class WeatherForecastAdapter(private val data: ForecastResponse
) : RecyclerView.Adapter<WeatherForecastAdapter.WeatherForecastViewHolder>() {
    private val toolBox = MethodLibrary()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherForecastViewHolder {
        return WeatherForecastViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.hourly_forecast, parent, false))
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(
        holder: WeatherForecastViewHolder,
        position: Int
    ) {
        val forecastItem = data.list[position]

        // Set time from timestamp
        holder.time?.text = toolBox.timeStampConvertor(forecastItem.dt)

        // Set temperatures
        holder.maxTemp?.text = "${toolBox.kelvinToCelsius(forecastItem.main.tempMax)} °C"
        holder.lowTemp?.text = "${toolBox.kelvinToCelsius(forecastItem.main.tempMin)} °C"

        // Set weather icon based on condition
        val weatherIcon = when (forecastItem.weather[0].main) {
            "Clouds" -> R.drawable.clouds
            "Clear" -> R.drawable.sun
            "Rain" -> R.drawable.rain
            "Snow" -> R.drawable.snow
            "Thunderstorm" -> R.drawable.thunderstorm
            else -> R.drawable.sun
        }
        holder.icon?.setImageResource(weatherIcon)
    }

    override fun getItemCount(): Int {
        return data.list.size
    }

    class WeatherForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val time: TextView? = itemView.findViewById(R.id.dayOfWeek)
        val icon: ImageView? = itemView.findViewById(R.id.weatherIcon)
        val maxTemp: TextView? = itemView.findViewById(R.id.highTemp)
        val lowTemp: TextView? = itemView.findViewById(R.id.lowTemp)
    }
}