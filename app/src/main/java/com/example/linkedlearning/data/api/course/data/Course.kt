package com.example.linkedlearning.data.api.course.data

data class Course(
    val EnrollmentCount: Int,
    val __v: Int,
    val _id: String,
    val categoryId: Category,
    val content: List<Content>,
    val createdAt: String,
    val descp: String,
    val image: String,
    val owner: Owner,
    val ratings: List<Rating>,
    val syllabus: List<Syllabu>,
    val title: String,
    val updatedAt: String
)