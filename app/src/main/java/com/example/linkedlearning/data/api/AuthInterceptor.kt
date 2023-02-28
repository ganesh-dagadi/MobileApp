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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException
import java.nio.file.attribute.AclEntry.newBuilder

class AuthInterceptor(context: Context) : Interceptor {
//    private val appContext = context.applicationContext
    val repoInstance = AuthRepo(context)
//    val retrofitInstance = ApiCore(context).getInstance().create(AuthAPI::class.java)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request:Request = chain.request()
        Log.i("APIEvent" , request.toString())
            val bearerToken = "Bearer " + runBlocking { repoInstance.getAccessToken() }
            var newRequest: Request = request.newBuilder()
                .header("Authorization", bearerToken)
                .build()

            var response = chain.proceed(newRequest)

            if (response.code == 401 && response.body != null) {
                //refresh the token
                val refreshToken ="Bearer "+runBlocking{repoInstance.getRefreshToken()}
                val mediaType = "application/json".toMediaTypeOrNull()
                val requestBody = RequestBody.create(mediaType, "{\"key\":\"value\"}")
                val refreshRequest = Request.Builder().url("https://api-linkedlearning.onrender.com/auth/newtoken")
                    .addHeader("Authorization" , refreshToken)
                    .patch(requestBody)
                    .build()
                val refreshClient = OkHttpClient()
                val refreshResponse = runBlocking { refreshClient.newCall(refreshRequest).execute()}
                if(refreshResponse.isSuccessful){
                    val responseBodyString = refreshResponse.body!!.string()
                    response.body!!.close()
                    val newAccessToken = responseBodyString.substring(10 , responseBodyString.length - 2)
                    runBlocking { repoInstance.setAccessToken(newAccessToken)}
                    val bearerToken = "Bearer " + runBlocking { repoInstance.getAccessToken() }
                    val recallRequest = request.newBuilder().addHeader("Authorization" , bearerToken).
                    build()
                    response = chain.proceed(recallRequest)
                }
            }
            return response
        }

    }