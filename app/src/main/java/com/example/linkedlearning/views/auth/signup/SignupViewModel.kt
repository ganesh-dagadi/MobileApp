package com.example.linkedlearning.views.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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

}