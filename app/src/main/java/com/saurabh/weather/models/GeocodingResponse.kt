package com.saurabh.weather.models

data class GeocodingResponse(
    val name: String,
    val local_names: Map<String, String>? = null,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String
)
