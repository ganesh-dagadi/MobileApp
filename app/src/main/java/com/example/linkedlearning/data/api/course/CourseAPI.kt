package com.example.linkedlearning.data.api.course

import com.example.linkedlearning.data.api.course.data.getCoursesRes
import retrofit2.Response
import retrofit2.http.GET

interface CourseAPI {
    @GET("/course/")
    suspend fun getAllCourses():Response<getCoursesRes>
}