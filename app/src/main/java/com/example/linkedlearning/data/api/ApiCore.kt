package com.example.linkedlearning.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiCore {
    var httpClient = OkHttpClient.Builder()
    private const val baseUrl = "https://api-linkedlearning.onrender.com/"
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl) //
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient.build())
        .build()
}