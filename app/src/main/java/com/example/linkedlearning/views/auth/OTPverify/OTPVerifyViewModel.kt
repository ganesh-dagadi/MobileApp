package com.example.linkedlearning.views.auth.OTPverify

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkedlearning.data.api.ApiCore
import com.example.linkedlearning.data.api.auth.AuthAPI
import com.example.linkedlearning.data.api.auth.data.OtpVerifyRes
import com.example.linkedlearning.data.api.auth.data.otpVerifyReq
import com.example.linkedlearning.data.authData.AuthRepo
import com.example.linkedlearning.views.UIevents
import com.google.gson.Gson
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.util.Timer

class OTPVerifyViewModel(context: Context): ViewModel() {
    val context = context
    private val repoInstance = AuthRepo(context)
    private var userId:String? = ""
    private val _otpVal = MutableLiveData<String>()
    private val _isResendActive = MutableLiveData<Boolean>()
    private val _remainingSeconds = MutableLiveData<Int>()
    val otpVal: LiveData<String>
        get() = _otpVal

    val isResendActive : LiveData<Boolean>
        get() = _isResendActive

    init{
        _otpVal.value = ""
        _remainingSeconds.value = 0
        _isResendActive.value = true
    }

    private val eventChannel = Channel<UIevents>()

    // Receiving channel as a flow
    val eventFlow = eventChannel.receiveAsFlow()

    fun triggerEvents(event:UIevents) = viewModelScope.launch{
        eventChannel.send(event)
    }

    fun setOTPVal(newString: String){
        _otpVal.value = newString
    }

    suspend fun verifyOTPReq():Boolean{
        userId = repoInstance.getUserId()
        val reqData = otpVerifyReq(_otpVal.value.toString() ,userId)
        val retrofitInstance = ApiCore(this.context).getInstance().create(AuthAPI::class.java)
        val response:Response<OtpVerifyRes> = try{
            retrofitInstance.verifyAccount(reqData)
        }catch(e:IOException){
            triggerEvents(UIevents.ShowErrorSnackBar("Please check your internet connection"))
            return false
        }catch(e:HttpException){
            triggerEvents(UIevents.ShowErrorSnackBar("Something went wrong. Try again later"))
            return false
        }

        if(response.code() == 200 && response.body() != null){
            triggerEvents(UIevents.ShowErrorSnackBar(response.body()!!.msg))
            return true
        }else{
            val errResponse = Gson().fromJson(response.errorBody()!!.string() , OtpVerifyRes::class.java)
            triggerEvents(UIevents.ShowErrorSnackBar(errResponse.err))
            return false
        }

    }

    fun setReset(bool:Boolean){
        _isResendActive.value = bool
    }

    suspend fun resendOTP(){
        setReset(false)
        userId = repoInstance.getUserId()
        val reqData = otpVerifyReq(userId = userId , otp = null)
        val retrofitInstance = ApiCore(this.context).getInstance().create(AuthAPI::class.java)
        val response:Response<OtpVerifyRes> = try{
            retrofitInstance.resendOTP(reqData)
        }catch(e:IOException){
            triggerEvents(UIevents.ShowErrorSnackBar("Please check your internet connection"))
            return
        }catch(e:HttpException){
            triggerEvents(UIevents.ShowErrorSnackBar("Something went wrong. Try again later"))
            return
        }

        if(response.code() == 200 && response.body() != null){
            triggerEvents(UIevents.ShowErrorSnackBar(response.body()!!.msg))
            return
        }else{
            val errResponse = Gson().fromJson(response.errorBody()!!.string() , OtpVerifyRes::class.java)
            triggerEvents(UIevents.ShowErrorSnackBar(errResponse.err))
        }
    }
}