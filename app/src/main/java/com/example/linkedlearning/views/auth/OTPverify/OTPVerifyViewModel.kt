package com.example.linkedlearning.views.auth.OTPverify

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linkedlearning.data.authData.AuthRepo
import kotlinx.coroutines.launch

class OTPVerifyViewModel(context: Context): ViewModel() {
    val repoInstance = AuthRepo(context)
    private val _otpVal = MutableLiveData<String>()
    val otpVal: LiveData<String>
        get() = _otpVal

    init{
        _otpVal.value = ""
    }

    fun setOTPVal(newString: String){
        _otpVal.value = newString
    }

    suspend fun getUserId(){
        val userId = repoInstance.getUserId()
        Log.i("responseMsg" , userId!!)
    }
}