package com.example.linkedlearning.data.courseData

interface CourseRepoIntr {
    suspend fun setSelectedCourseId(_id :String):Unit
    suspend fun getSelectedCourseId():String?

    suspend fun setSelectedLectureId(_id:String):Unit
    suspend fun getSelectedLectureId():String?
}