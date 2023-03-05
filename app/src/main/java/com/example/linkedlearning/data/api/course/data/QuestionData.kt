package com.example.linkedlearning.data.api.course.data

data class QuestionReq(
    private val question:QuestionData
)
data class QuestionData(
    private val title : String,
    private val descp : String
)
