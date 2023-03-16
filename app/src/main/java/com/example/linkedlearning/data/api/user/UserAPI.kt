package com.example.linkedlearning.data.api.user

import android.service.autofill.UserData
import com.example.linkedlearning.data.api.course.data.Owner
import com.example.linkedlearning.data.api.user.data.GetUserRes
import retrofit2.Response
import retrofit2.http.GET

interface UserAPI {
    @GET("/user/")
    suspend fun getUserData():Response<GetUserRes>
}