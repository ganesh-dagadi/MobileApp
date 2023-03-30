package com.example.linkedlearning.views.dashboard

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkedlearning.data.api.ApiCore
import com.example.linkedlearning.data.api.auth.data.LoginRes
import com.example.linkedlearning.data.api.course.CourseAPI
import com.example.linkedlearning.data.api.course.data.Category
import com.example.linkedlearning.data.api.course.data.Course
import com.example.linkedlearning.data.courseData.CourseRepo
import com.example.linkedlearning.views.UIevents
import com.google.gson.Gson
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DashBoardViewModel(private val context:Context): ViewModel() {
    private val repoInstance = CourseRepo(context)
    private val _coursesList = MutableLiveData<List<Course>>()
    val coursesList: LiveData<List<Course>>
        get() = _coursesList

//    private val _searchText = MutableLiveData<String>()
//    val searchText: LiveData<String>
//        get() = _searchText

    private val _enrolledCoursesList = MutableLiveData<List<Course>>()
    val enrolledCoursesList: LiveData<List<Course>>
        get() = _enrolledCoursesList

    private val _categoriesList = MutableLiveData<List<Category>>()
    val categoryList: LiveData<List<Category>>
        get() = _categoriesList

    private val retrofitInstance = ApiCore(this.context).getInstance().create(CourseAPI::class.java)

    private val eventChannel = Channel<UIevents>()

    // Receiving channel as a flow
    val eventFlow = eventChannel.receiveAsFlow()
    fun triggerEvents(event:UIevents) = viewModelScope.launch{
        eventChannel.send(event)
    }
    suspend fun getAllCourses():Boolean{

        val response = try{
            retrofitInstance.getAllCourses()
        }catch(e:IOException){
            triggerEvents(UIevents.ShowErrorSnackBar("Please check your internet connection"))
            return false
        }catch(e:HttpException){
            triggerEvents(UIevents.ShowErrorSnackBar(msg = "Something went wrong. Please try again later"))
            return false
        }
        if(response.code() == 200 && response.body() != null){

            this._coursesList.value = response.body()!!.courses;

        }else if(response.errorBody() != null){

        val errResponse = Gson().fromJson(response.errorBody()!!.string() , LoginRes::class.java)
        triggerEvents(UIevents.ShowErrorSnackBar(errResponse.err))
        return false
    }
        return false
    }

    suspend fun getCategories():Boolean{
        val response = try{
            retrofitInstance.getAllCategories()
        }catch(e:IOException){
            triggerEvents(UIevents.ShowErrorSnackBar("Check your internet connection and try again"))
            return false
        }catch(e:HttpException){
            triggerEvents(UIevents.ShowErrorSnackBar("Something went wrong try again later"))
            return false
        }
        if(response.code() == 200 && response.body() != null){
            this._categoriesList.value = response.body()!!.categories
            return true
        }
        return false
    }

    suspend fun setSelectedCourseId(_id:String){
        repoInstance.setSelectedCourseId(_id)
    }

    suspend fun getEnrolledCourses():Boolean{
        val response = try{
            Log.i("APIEvent" , "Hit")
            retrofitInstance.getEnrolledCourses()
        }catch(e:IOException){
            triggerEvents(UIevents.ShowErrorSnackBar("Check your internet connection and try again"))
            return false
        }catch(e:HttpException){
            triggerEvents(UIevents.ShowErrorSnackBar("Something went wrong try again later"))
            return false
        }
        if(response.code() == 200 && response.body() != null){
            this._enrolledCoursesList.value = response.body()!!.enrolledCourses
        }
        return false
    }

    suspend fun getCoursesByCategory(_id:String):Boolean{
        val response = try{
            retrofitInstance.getCoursesByCategory(_id)
        }catch(e:IOException){
            triggerEvents(UIevents.ShowErrorSnackBar("Check your internet connection and try again"))
            return false
        }catch(e:HttpException){
            triggerEvents(UIevents.ShowErrorSnackBar("Something went wrong try again later"))
            return false
        }
        if(response.code() == 200 && response.body() != null){
            this._coursesList.value = response.body()!!.courses;
            Log.i("APIEvent" , "Hit")
            Log.i("APIEvent" , this._coursesList.value.toString())
        }

        return false
    }

    suspend fun searchCourses(searchStr:String):Boolean{
        val response = try{
            retrofitInstance.searchCourses(searchStr);
        }catch(e:IOException){
            triggerEvents(UIevents.ShowErrorSnackBar("Check your internet connection and try again"))
            return false
        }catch(e:HttpException){
            triggerEvents(UIevents.ShowErrorSnackBar("Something went wrong try again later"))
            return false
        }
        if(response.code() == 200 && response.body() != null){
            this._coursesList.value = response.body()!!.courses;
            Log.i("APIEvent" , "Hit")
            Log.i("APIEvent" , this._coursesList.value.toString())
        }

        return false
    }
}