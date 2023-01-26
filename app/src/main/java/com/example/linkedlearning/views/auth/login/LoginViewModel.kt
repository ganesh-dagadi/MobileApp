package com.example.linkedlearning.views.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
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
}