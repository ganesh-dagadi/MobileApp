package com.example.linkedlearning.data.api.course.data

data class Question(
    val __v: Int,
    val _id: String,
    val answers: List<Answer>,
    val descp: String?,
    val owner: Owner,
    val title: String
)