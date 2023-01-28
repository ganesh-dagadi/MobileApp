package com.example.linkedlearning.views.auth.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkedlearning.views.UIevents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SignupViewModel : ViewModel() {
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

    init {
        _email.value = ""
        _password.value = ""
        _confirmPassword.value = ""
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

    suspend fun signupUser(){
        if(_email.value!!.isEmpty() || _password.value!!.isEmpty()){
            Log.i("errorMsg" , "Empty Parameters")
            triggerEvents(UIevents.ShowErrorSnackBar("All parameters required" , ))
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(_email.value).matches()){
            Log.i("error" , "Email format")
            triggerEvents(UIevents.ShowErrorSnackBar("Enter the correct email"))
        }
        else if(_password.value != _confirmPassword.value){
            Log.i("error" , "Passwords dont match")
            triggerEvents(UIevents.ShowErrorSnackBar("Passwords dont match"))
        }else if(_isChecked.value == false){
            Log.i("error" , "Checkbox unchecked")
            triggerEvents(UIevents.ShowErrorSnackBar("Accept the terms and conditions"))
        }else{

        }



    }



}