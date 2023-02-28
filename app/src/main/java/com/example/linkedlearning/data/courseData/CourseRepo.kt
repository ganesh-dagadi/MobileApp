package com.example.linkedlearning.data.courseData

import android.content.Context

class CourseRepo(context:Context):CourseRepoIntr {
    val appContext = context.applicationContext
    private val sf = appContext.getSharedPreferences("courseData" , Context.MODE_PRIVATE)

    override suspend fun setSelectedCourseId(_id: String) {
        with(sf.edit()){
            putString("courseId" , _id)
            apply()
        }
    }

    override suspend fun getSelectedCourseId(): String? {
        return sf.getString("courseId", null)
    }
}