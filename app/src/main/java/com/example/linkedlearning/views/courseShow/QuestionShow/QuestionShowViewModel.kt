package com.example.linkedlearning.views.courseShow.QuestionShow

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkedlearning.data.api.ApiCore
import com.example.linkedlearning.data.api.auth.data.LoginRes
import com.example.linkedlearning.data.api.course.CourseAPI
import com.example.linkedlearning.data.api.course.data.Answer
import com.example.linkedlearning.data.api.course.data.AnswerReqData
import com.example.linkedlearning.data.api.course.data.Question
import com.example.linkedlearning.data.courseData.CourseRepo
import com.example.linkedlearning.views.UIevents
import com.google.gson.Gson
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class QuestionShowViewModel(private val context:Context): ViewModel() {
    private val repoInstance = CourseRepo(context)
    private val eventChannel = Channel<UIevents>()

    private val _newAnswerText = MutableLiveData<String>()
    val newAnswerText : LiveData<String>
    get() = _newAnswerText

    init{
        this._newAnswerText.value = ""
    }
    private val _questionData = MutableLiveData<Question>()
    val questionData : LiveData<Question>
    get() = _questionData

    // Receiving channel as a flow
    val eventFlow = eventChannel.receiveAsFlow()
    private val retrofitInstance = ApiCore(this.context).getInstance().create(CourseAPI::class.java)

    private fun triggerEvents(event: UIevents) = viewModelScope.launch{
        eventChannel.send(event)
    }
    suspend fun getQuestionData():Boolean{
        val lectureId = repoInstance.getSelectedLectureId()
        val courseId = repoInstance.getSelectedCourseId()

        val response = try{
            retrofitInstance.getQuestionById(courseId!! , lectureId!!)
        }catch(e: IOException){
            triggerEvents(UIevents.ShowErrorSnackBar("Please check your internet connection"))
            return false
        }catch(e: HttpException){
            triggerEvents(UIevents.ShowErrorSnackBar(msg = "Something went wrong. Please try again later"))
            return false
        }

        if(response.code() == 200 && response.body() != null){
            this._questionData.value = response.body()!!.question
            return true
        }

    return false
    }

    fun setAnsText(text:String){
        this._newAnswerText.value = text
    }

    suspend fun postAnswerQuestion():Boolean{
        val courseId = repoInstance.getSelectedCourseId();
        val lectureId = repoInstance.getSelectedLectureId();
        val answerBody = Answer(null , this._newAnswerText.value , null)
        val reqBody = AnswerReqData(answer = answerBody)
        val response = try{
            retrofitInstance.createAnswer(courseId , lectureId , reqBody)
        }catch(e: IOException){
            triggerEvents(UIevents.ShowErrorSnackBar("Please check your internet connection"))
            return false
        }catch(e: HttpException){
            triggerEvents(UIevents.ShowErrorSnackBar(msg = "Something went wrong. Please try again later"))
            return false
        }

        if(response.code() == 200 && response.body() != null){
            return true
        }else{
            return false
        }
        return false
    }
}