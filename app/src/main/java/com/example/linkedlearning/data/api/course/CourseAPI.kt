package com.example.linkedlearning.data.api.course

import com.example.linkedlearning.data.api.course.data.*
import retrofit2.Response
import retrofit2.http.*

interface CourseAPI {
    @GET("/course/")
    suspend fun getAllCourses():Response<getCoursesRes>

    @GET("/course/category")
    suspend fun getAllCategories():Response<getCategoryRes>

    @GET("/course/details/{course_id}")
    suspend fun getCourseById(@Path(value = "course_id", encoded = true)course_id:String):Response<getOneCourseRes>

    @PATCH("/course/{course_id}/enroll")
    suspend fun enrollIntoCourse(@Path(value = "course_id", encoded = true)course_id:String):Response<NormalMsg>

    @GET("/course/enrolled")
    suspend fun getEnrolledCourses():Response<getEnrollCoursesRes>

    @GET("/course/bycat")
    suspend fun getCoursesByCategory(@Query("categoryId") category_id:String):Response<getCoursesRes>

    @POST("/course/{course_id}/question")
    suspend fun createQuestion(@Body questionData:QuestionReq , @Path(value = "course_id", encoded = true)course_id: String):Response<NormalMsg>

    @GET("/course/{course_id}/question")
    suspend fun getAllQuestions(@Path(value = "course_id", encoded = true)course_id: String):Response<DiscussionQuestions>
}