package com.project.weather.apiservices

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASEURL ="https://api.openweathermap.org/"

    private const val REVERSEBASEURL = "https://api.opencagedata.com/"

    private val GEOCODING_URL= "https://api.openweathermap.org/geo/1.0/"


    val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()

    val retrofit: ApiServices by lazy {
        Retrofit.Builder()
            .baseUrl(BASEURL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServices::class.java)
    }

    val reverseApi: ReverseApiService by lazy {
        Retrofit.Builder()
            .baseUrl(REVERSEBASEURL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ReverseApiService::class.java)
    }

    val geocoding: ApiServices by lazy {
        Retrofit.Builder()
            .baseUrl(GEOCODING_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServices::class.java)
    }



}