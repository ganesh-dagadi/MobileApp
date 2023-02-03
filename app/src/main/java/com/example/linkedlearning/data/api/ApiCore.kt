package com.example.linkedlearning.data.api

import android.content.Context
import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiCore(context: Context) {
    var httpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(context))
      private val baseUrl = "https://api-linkedlearning.onrender.com/"
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl) //
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient.build())
        .build()

    fun getInstance():Retrofit{
        Log.i("APIEvent" , "Entered getiNSTANCE")
        return retrofit
    }
}