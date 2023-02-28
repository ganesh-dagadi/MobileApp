package com.example.linkedlearning.data.api.course.data

data class Rating(
    val _id:String,
    val userId : String,
    val rate : Int,
    val __v : Int,
    val courseId : String
)
