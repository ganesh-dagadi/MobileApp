package com.example.linkedlearning.data.api

import android.content.Context
import android.util.Log
import com.example.linkedlearning.data.api.auth.AuthAPI
import com.example.linkedlearning.data.api.auth.data.LoginRes
import com.example.linkedlearning.data.api.auth.data.RefreshResponse
import com.example.linkedlearning.data.authData.AuthRepo
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.runBlocking
import okhttp3.*
import java.io.IOException
import java.nio.file.attribute.AclEntry.newBuilder

class AuthInterceptor(context: Context) : Interceptor {
//    private val appContext = context.applicationContext
    val repoInstance = AuthRepo(context)
//    val retrofitInstance = ApiCore(context).getInstance().create(AuthAPI::class.java)
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.i("APIEvent" , "In interceptor entered")
        val request:Request = chain.request()

            Log.i("APIEvent" , "Normal request")
            val bearerToken = "Bearer " + runBlocking { repoInstance.getAccessToken() }
            val newRequest: Request = request.newBuilder()
                .header("Authorization", bearerToken)
                .build()

            val response = chain.proceed(newRequest)

            if (response.code() == 401 && response.body() != null) {
                Log.i("APIEvent" , "Token expired")
                //refresh the token
                val refreshToken ="Bearer "+runBlocking{repoInstance.getRefreshToken()}
                val mediaType = MediaType.parse("application/json")
                val requestBody = RequestBody.create(mediaType, "{\"key\":\"value\"}")
                val refreshRequest = Request.Builder().url("https://6d7a-2405-201-d004-104b-a4b8-60d0-2bc4-7c1b.ngrok.io/auth/newtoken")
                    .addHeader("Authorization" , refreshToken)
                    .patch(requestBody)
                    .build()
                val refreshClient = OkHttpClient()
//                Log.i("APIEvent" , "Crashing")
                val refreshResponse = runBlocking { refreshClient.newCall(refreshRequest).execute()}
                if(refreshResponse.isSuccessful){
//                    Log.i("APIEvent" , refreshResponse.body()!!.string())
                        val responseBodyString = refreshResponse.body()!!.string()
                        val newAccessToken = responseBodyString.substring(10 , responseBodyString.length - 2)
                        Log.i("APIEvent" , "After crash")
                        Log.i("APIEvent" , newAccessToken)
                        runBlocking { repoInstance.setAccessToken(newAccessToken)}
                        Log.i("APIEvent" , "Set token")
                        val bearerToken = "Bearer " + runBlocking { repoInstance.getAccessToken() }
                        Log.i("APIEvent" , bearerToken)
                        Log.i("APIEvent" , "Before crash")
                        val recallRequest = request.newBuilder().addHeader("Authorization" , bearerToken).
                        build()

                        Log.i("APIEvent" , "After crash")
                        return chain.proceed(request)
                }
            }
            return response
        }

    }