package com.example.linkedlearning.views.auth.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkedlearning.data.api.ApiCore
import com.example.linkedlearning.data.api.auth.AuthAPI
import com.example.linkedlearning.data.api.auth.data.LoginReq
import com.example.linkedlearning.data.api.auth.data.LoginRes
import com.example.linkedlearning.data.api.auth.data.SignupResponse
import com.example.linkedlearning.data.authData.AuthRepo
import com.example.linkedlearning.views.UIevents
import com.google.gson.Gson
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class LoginViewModel(context : Context) : ViewModel() {
    private val repoInstance = AuthRepo(context)
    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
    get() = _email

    //password
    private val _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password
    init {
        _email.value = ""
        _password.value = ""
    }

    fun setEmailText(newText:String){
        _email.value = newText
    }
    fun setPasswordText(newText:String){
        _password.value = newText
    }

    private val eventChannel = Channel<UIevents>()

    // Receiving channel as a flow
    val eventFlow = eventChannel.receiveAsFlow()

    fun triggerEvents(event:UIevents) = viewModelScope.launch{
        eventChannel.send(event)
    }

    suspend fun login():Boolean{
        if(_email.value!!.isEmpty() || _password.value!!.isEmpty()){
            triggerEvents(UIevents.ShowErrorSnackBar("Fill all the fields"))
        }else{
            val reqData = LoginReq(_email.value.toString() , _password.value.toString())
            val retrofitInstance = ApiCore.retrofit.create(AuthAPI::class.java)
            val response = try{
                retrofitInstance.login(reqData)
            }catch(e:IOException){
                triggerEvents(UIevents.ShowErrorSnackBar("Please check your internet connection"))
                return false
            }catch(e:HttpException){
                triggerEvents(UIevents.ShowErrorSnackBar("Please check your internet connection"))
                return false
            }
            if(response.code() == 200 && response.body()!= null){
                repoInstance.setAccessToken(response.body()!!.tokens.access)
                repoInstance.setRefreshToken(response.body()!!.tokens.refresh)
                // Todo save returned user details in room database
                repoInstance.setLoginState(true)
                return true
            }else if(response.errorBody() != null){

                val errResponse = Gson().fromJson(response.errorBody()!!.string() , LoginRes::class.java)
                Log.i("responseMsg" , errResponse.err)
                triggerEvents(UIevents.ShowErrorSnackBar(errResponse.err))
                return false
            }
        }
        return false
    }
}