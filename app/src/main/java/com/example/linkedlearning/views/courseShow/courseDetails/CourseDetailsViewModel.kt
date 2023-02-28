package com.example.linkedlearning.views.courseShow.courseDetails

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkedlearning.data.api.ApiCore
import com.example.linkedlearning.data.api.auth.data.LoginRes
import com.example.linkedlearning.data.api.course.CourseAPI
import com.example.linkedlearning.data.api.course.data.Course
import com.example.linkedlearning.data.courseData.CourseRepo
import com.example.linkedlearning.views.UIevents
import com.google.gson.Gson
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class CourseDetailsViewModel(private val context : Context):ViewModel() {
    private val repoInstance = CourseRepo(context)

    private val _courseData = MutableLiveData<Course>()
    val courseData : LiveData<Course>
    get() = _courseData

    private val retrofitInstance = ApiCore(this.context).getInstance().create(CourseAPI::class.java)

    private val eventChannel = Channel<UIevents>()

    // Receiving channel as a flow
    val eventFlow = eventChannel.receiveAsFlow()
    fun triggerEvents(event: UIevents) = viewModelScope.launch {
        eventChannel.send(event)
    }

    suspend fun getCourseData():Boolean{
        val courseId = repoInstance.getSelectedCourseId()

        val response = try{
            retrofitInstance.getCourseById(courseId!!)
        }catch(e:IOException){
            triggerEvents(UIevents.ShowErrorSnackBar("Please check your internet connection"))
            return false
        }catch(e: HttpException){
            triggerEvents(UIevents.ShowErrorSnackBar(msg = "Something went wrong. Please try again later"))
            return false
        }
        Log.i("APIEvent" , response.body()!!.toString())
        if(response.code() == 200 && response.body() != null){
//            this._courseData.value = response.body()!!.foundCourse
        }else if(response.errorBody() != null){
            val errResponse = Gson().fromJson(response.errorBody()!!.string() , LoginRes::class.java)
            triggerEvents(UIevents.ShowErrorSnackBar(errResponse.err))
            return false
        }
        return false
    }
}