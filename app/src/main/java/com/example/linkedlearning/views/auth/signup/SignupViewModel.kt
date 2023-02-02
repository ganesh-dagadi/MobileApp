package com.example.linkedlearning.views.auth.signup

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.linkedlearning.data.api.ApiCore
import com.example.linkedlearning.data.api.auth.AuthAPI
import com.example.linkedlearning.data.api.auth.data.CreateUser
import com.example.linkedlearning.data.api.auth.data.SignupResponse
import com.example.linkedlearning.data.authData.AuthRepo
import com.example.linkedlearning.data.authData.AuthRepoImpl
import com.example.linkedlearning.views.UIevents
import com.google.gson.Gson
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException



class SignupViewModel(context:Context) : ViewModel(){
    val context = context
    val repoInstance = AuthRepo(context)
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

    private val _confirmPassword = MutableLiveData<String>()
    val confirmPassword: LiveData<String>
        get() = _confirmPassword

    private val _isChecked = MutableLiveData<Boolean>()
    val isChecked: LiveData<Boolean>
        get() = _isChecked

    private val _username = MutableLiveData<String>()
    val username: LiveData<String>
        get() = _username
    init {
        _email.value = ""
        _password.value = ""
        _confirmPassword.value = ""
        _username.value = ""
        _isChecked.value = false
    }

    fun setConfirmPassword(newText: String){
        _confirmPassword.value = newText
    }

    fun setEmailText(newText:String){
        _email.value = newText
    }
    fun setPasswordText(newText:String){
        _password.value = newText
    }

    fun setUsernameText(newText:String){
        _username.value = newText
    }

    fun toggleIsChecked(){
        _isChecked.value = !(_isChecked.value)!!
    }


    private val eventChannel = Channel<UIevents>()

    // Receiving channel as a flow
    val eventFlow = eventChannel.receiveAsFlow()

    fun triggerEvents(event:UIevents) = viewModelScope.launch{
        eventChannel.send(event)
    }

    // Authentication functions

    suspend fun signupUser():Boolean{

        if(_email.value!!.isEmpty() || _password.value!!.isEmpty()){
            triggerEvents(UIevents.ShowErrorSnackBar("All parameters required"))
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(_email.value).matches()){
            triggerEvents(UIevents.ShowErrorSnackBar("Enter the correct email"))
        }
        else if(_password.value != _confirmPassword.value){
            triggerEvents(UIevents.ShowErrorSnackBar("Passwords don't match"))
        }else if(_isChecked.value == false){
            triggerEvents(UIevents.ShowErrorSnackBar("Accept the terms and conditions"))
        }else{
            //All validations passed
            val retrofitInstance = ApiCore(this.context).getInstance().create(AuthAPI::class.java)
            val userData = CreateUser(
                _email.value.toString(),
                false ,
                _password.value.toString() , _username.value.toString())
            val response = try{
                retrofitInstance.createUser(userData)
            }catch(e:IOException){
                triggerEvents(UIevents.ShowErrorSnackBar("Please check your internet connection"))
                return false
            }catch(e:HttpException){
                triggerEvents(UIevents.ShowErrorSnackBar("Something went wrong. Try again later"))
                return false
            }
            if(response.code() == 200 && response.body() != null){
                triggerEvents(UIevents.ShowErrorSnackBar(response.body()!!.msg))
                repoInstance.setUserId(response.body()!!.user._id)
                return true
            }else if(response.errorBody() != null){
                val errResponse = Gson().fromJson(response.errorBody()!!.string() , SignupResponse::class.java)
                triggerEvents(UIevents.ShowErrorSnackBar(errResponse.err))
                return false
            }
        }

        return false

    }



}