package com.hsappcreators.weather.models

data class AirQualityResponse(
    val coord: AirCoord,
    val list: List<AirQualityItem>
)

data class AirCoord(
    val lon: Double,
    val lat: Double
)

data class AirQualityItem(
    val dt: Long,
    val main: AQIMain,
    val components: AirComponents
)

data class AQIMain(
    val aqi: Int
)

data class AirComponents(
    val co: Double,
    val no: Double,
    val no2: Double,
    val o3: Double,
    val so2: Double,
    val pm2_5: Double,
    val pm10: Double,
    val nh3: Double
)
