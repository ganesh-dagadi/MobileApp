package com.example.linkedlearning.views.courseShow.NewQuestion

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkedlearning.data.api.ApiCore
import com.example.linkedlearning.data.api.auth.AuthAPI
import com.example.linkedlearning.data.api.auth.data.LoginRes
import com.example.linkedlearning.data.api.course.CourseAPI
import com.example.linkedlearning.data.api.course.data.QuestionData
import com.example.linkedlearning.data.api.course.data.QuestionReq
import com.example.linkedlearning.data.courseData.CourseRepo
import com.example.linkedlearning.views.UIevents
import com.google.gson.Gson
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class NewQuestionViewModel(private val context:Context):ViewModel() {
    private val repoInstance = CourseRepo(context)
    private val _title = MutableLiveData<String>()
    val title : LiveData<String>
    get() = _title

    private val _descp = MutableLiveData<String>()
    val descp : LiveData<String>
        get() = _descp

    private val eventChannel = Channel<UIevents>()

    // Receiving channel as a flow
    val eventFlow = eventChannel.receiveAsFlow()
    val retrofitInstance = ApiCore(this.context).getInstance().create(CourseAPI::class.java)

    fun triggerEvents(event: UIevents) = viewModelScope.launch{
        eventChannel.send(event)
    }
    init {
        this._title.value = ""
        this._descp.value = ""
    }

    fun setTitleText(text : String){
        this._title.value = text
    }

    fun setDescpText(text : String){
        this._descp.value = text
    }

    suspend fun postNewQuestion():Boolean{
        val courseId = repoInstance.getSelectedCourseId()
        val questionData = QuestionData(this._title.value!! , this._descp.value!!)
        val reqData = QuestionReq(questionData);
        val response = try{
            retrofitInstance.createQuestion(reqData , courseId!!)
        }catch(e: IOException){
            triggerEvents(UIevents.ShowErrorSnackBar("Please check your internet connection"))
            return false
        }catch(e: HttpException){
            triggerEvents(UIevents.ShowErrorSnackBar(msg = "Something went wrong. Please try again later"))
            return false
        }

        if(response.code() == 200 && response.body() != null){
            return true;
        }else if(response.errorBody() != null){

            val errResponse = Gson().fromJson(response.errorBody()!!.string() , LoginRes::class.java)
            Log.i("responseMsg" , errResponse.err)
            triggerEvents(UIevents.ShowErrorSnackBar(errResponse.err))
            return false
        }
        return false
    }
}