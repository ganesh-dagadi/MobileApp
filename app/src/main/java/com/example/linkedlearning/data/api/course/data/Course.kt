package com.example.linkedlearning.data.api.course.data

data class Course(
    val __v: Int,
    val _id: String,
    val categoryId: String,
    val content: List<Content>,
    val createdAt: String,
    val descp: String,
    val image: String,
    val owner: Owner,
    val syllabus: List<Syllabu>,
    val title: String,
    val updatedAt: String
)