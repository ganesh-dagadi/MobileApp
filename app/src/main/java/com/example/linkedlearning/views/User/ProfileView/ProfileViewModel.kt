package com.example.linkedlearning.views.User.ProfileView

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkedlearning.data.api.ApiCore
import com.example.linkedlearning.data.api.course.CourseAPI
import com.example.linkedlearning.data.api.user.UserAPI
import com.example.linkedlearning.views.UIevents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ProfileViewModel(private val context: Context):ViewModel() {

    var username:String = "123"
    var email:String = "456"
    var imageUrl:String = "321"


    private val eventChannel = Channel<UIevents>()

    // Receiving channel as a flow
    val eventFlow = eventChannel.receiveAsFlow()
    fun triggerEvents(event: UIevents) = viewModelScope.launch{
        eventChannel.send(event)
    }


    private val retrofitInstance = ApiCore(this.context).getInstance().create(UserAPI::class.java)

    suspend fun getUserData():Boolean{
        val response = try{
            retrofitInstance.getUserData()
        }catch(e: IOException){
            triggerEvents(UIevents.ShowErrorSnackBar("Check your internet connection and try again"))
            return false
        }catch(e: HttpException){
            triggerEvents(UIevents.ShowErrorSnackBar("Something went wrong try again later"))
            return false
        }
        if(response.code() == 200 && response.body() != null){
            Log.i("APIEvent" , response.body().toString())
            username = response.body()!!.userData.username
            email = response.body()!!.userData.email
            imageUrl = response.body()!!.userData.image
            return true
        }
        return false
    }
}