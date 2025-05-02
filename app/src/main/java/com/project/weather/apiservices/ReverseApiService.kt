package com.project.weather.apiservices

import com.project.weather.models.OpenCageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ReverseApiService {

    @GET("geocode/v1/json")
    suspend fun getAccurateLocation(
        @Query("key") apiKey: String,
        @Query("q") location: String,
    ): OpenCageResponse

}