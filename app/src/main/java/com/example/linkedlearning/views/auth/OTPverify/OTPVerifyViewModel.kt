package com.example.linkedlearning.views.auth.OTPverify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OTPVerifyViewModel: ViewModel() {
    private val _otpVal = MutableLiveData<String>()
    val otpVal: LiveData<String>
        get() = _otpVal

    init{
        _otpVal.value = ""
    }

    fun setOTPVal(newString: String){
        _otpVal.value = newString
    }
}