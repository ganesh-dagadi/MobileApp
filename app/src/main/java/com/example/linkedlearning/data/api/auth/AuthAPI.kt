package com.example.linkedlearning.data.api.auth

import com.example.linkedlearning.data.api.auth.data.CreateUser
import com.example.linkedlearning.data.api.auth.data.SignupResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface AuthAPI {

    @POST("auth/signup")
    suspend fun createUser(@Body user: CreateUser): Response<SignupResponse>
}