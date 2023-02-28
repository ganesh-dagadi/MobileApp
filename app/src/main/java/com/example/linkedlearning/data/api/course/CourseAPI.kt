package com.example.linkedlearning.data.api.course

import com.example.linkedlearning.data.api.course.WILLDEL.getOneCOurseResponse
import com.example.linkedlearning.data.api.course.data.Course
import com.example.linkedlearning.data.api.course.data.getCategoryRes
import com.example.linkedlearning.data.api.course.data.getCoursesRes
import com.example.linkedlearning.data.api.course.data.getOneCourseRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CourseAPI {
    @GET("/course/")
    suspend fun getAllCourses():Response<getCoursesRes>

    @GET("/course/category")
    suspend fun getAllCategories():Response<getCategoryRes>

    @GET("/course/details/{course_id}")
    suspend fun getCourseById(@Path(value = "course_id", encoded = true)course_id:String):Response<getOneCourseRes>
}