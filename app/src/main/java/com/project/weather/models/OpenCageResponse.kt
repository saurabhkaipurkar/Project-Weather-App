package com.project.weather.models

data class OpenCageResponse(
    val documentation: String,
    val licenses: List<License>,
    val rate: Rate,
    val results: List<Result>,
    val status: Status,
    val stay_informed: StayInformed,
    val thanks: String,
    val timestamp: Timestamp,
    val total_results: Int
)

data class License(
    val name: String,
    val url: String
)

data class Rate(
    val limit: Int,
    val remaining: Int,
    val reset: Long
)

data class Result(
    val bounds: Bounds,
    val components: Components,
    val confidence: Int,
    val distance_from_q: DistanceFromQ,
    val formatted: String,
    val geometry: Geometry
)

data class Bounds(
    val northeast: LatLng,
    val southwest: LatLng
)

data class LatLng(
    val lat: Double,
    val lng: Double
)

data class Components(
    val ISO_3166_1_alpha_2: String,
    val ISO_3166_1_alpha_3: String,
    val ISO_3166_2: List<String>,
    val _category: String,
    val _normalized_city: String,
    val _type: String,
    val city_district: String,
    val continent: String,
    val country: String,
    val country_code: String,
    val county: String,
    val road: String,
    val road_reference: String,
    val road_type: String,
    val state: String,
    val state_code: String,
    val state_district: String,
    val town: String
)

data class DistanceFromQ(
    val meters: Int
)

data class Geometry(
    val lat: Double,
    val lng: Double
)

data class Status(
    val code: Int,
    val message: String
)

data class StayInformed(
    val blog: String,
    val mastodon: String
)

data class Timestamp(
    val created_http: String,
    val created_unix: Long
)

