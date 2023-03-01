package com.example.linkedlearning.data.api.course

import com.example.linkedlearning.data.api.course.data.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface CourseAPI {
    @GET("/course/")
    suspend fun getAllCourses():Response<getCoursesRes>

    @GET("/course/category")
    suspend fun getAllCategories():Response<getCategoryRes>

    @GET("/course/details/{course_id}")
    suspend fun getCourseById(@Path(value = "course_id", encoded = true)course_id:String):Response<getOneCourseRes>

    @PATCH("/course/{course_id}/enroll")
    suspend fun enrollIntoCourse(@Path(value = "course_id", encoded = true)course_id:String):Response<NormalMsg>
}