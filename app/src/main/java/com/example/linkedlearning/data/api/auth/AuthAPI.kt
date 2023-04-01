package com.example.linkedlearning.data.api.auth

import com.example.linkedlearning.data.api.auth.data.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query


interface AuthAPI {

    @POST("auth/signup")
    suspend fun createUser(@Body user: CreateUser): Response<SignupResponse>

    @POST("auth/signup/verify")
    suspend fun verifyAccount(@Body otp:otpVerifyReq): Response<OtpVerifyRes>

    @PATCH("auth/signup/resendotp")
    suspend fun resendOTP(@Body otp:otpVerifyReq):Response<OtpVerifyRes>

    @PATCH("auth/login")
    suspend fun login(@Body user:LoginReq):Response<LoginRes>

    @PATCH("auth/newtoken")
    suspend fun refreshToken():Response<LoginRes>

    @GET("auth/protected")
    suspend fun getProtected():Response<LoginRes>

    @PATCH("auth/logout")
    suspend fun logoutUser():Response<SignupResponse>
}