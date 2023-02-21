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
//        private val baseUrl = "https://908a-2405-201-d004-104b-5a28-e348-329a-3a36.in.ngrok.io/"
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