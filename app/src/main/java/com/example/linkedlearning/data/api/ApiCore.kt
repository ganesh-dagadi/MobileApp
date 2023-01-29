package com.example.linkedlearning.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiCore {
    var httpClient = OkHttpClient.Builder()
    private const val baseUrl = "https://api-linkedlearning.onrender.com/"
//      private const val baseUrl = "https://e701-2405-201-d004-104b-4516-eb23-cf56-29d6.ngrok.io"
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl) //
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient.build())
        .build()
}