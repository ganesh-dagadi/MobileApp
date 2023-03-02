package com.example.linkedlearning.views.courseShow.EnrolledCourses

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkedlearning.data.api.ApiCore
import com.example.linkedlearning.data.api.course.CourseAPI
import com.example.linkedlearning.data.api.course.data.Content
import com.example.linkedlearning.data.api.course.data.Course
import com.example.linkedlearning.data.courseData.CourseRepo
import com.example.linkedlearning.views.UIevents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class EnrolledCoursesViewModel(private val context: Context):ViewModel() {
    private val repoInstance = CourseRepo(context)
    private val _enrolledCoursesList = MutableLiveData<List<Course>>()
    val enrolledCoursesList: LiveData<List<Course>>
        get() = _enrolledCoursesList

    private val retrofitInstance = ApiCore(this.context).getInstance().create(CourseAPI::class.java)

    private val eventChannel = Channel<UIevents>()

    fun triggerEvents(event:UIevents) = viewModelScope.launch{
        eventChannel.send(event)
    }

    suspend fun setSelectedCourseId(_id:String){
        repoInstance.setSelectedCourseId(_id)
    }

    suspend fun getEnrolledCourses():Boolean{
        val response = try{
            Log.i("APIEvent" , "Hit")
            retrofitInstance.getEnrolledCourses()
        }catch(e: IOException){
            triggerEvents(UIevents.ShowErrorSnackBar("Check your internet connection and try again"))
            return false
        }catch(e: HttpException){
            triggerEvents(UIevents.ShowErrorSnackBar("Something went wrong try again later"))
            return false
        }
        if(response.code() == 200 && response.body() != null){
            this._enrolledCoursesList.value = response.body()!!.enrolledCourses
        }
        return false
    }
}