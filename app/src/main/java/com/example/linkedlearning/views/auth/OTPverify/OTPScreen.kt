package com.example.linkedlearning.views.auth.OTPverify

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.linkedlearning.R
import com.example.linkedlearning.Utils.Routes
import com.example.linkedlearning.components.TimerComponent
import com.example.linkedlearning.data.authData.AuthRepo
import com.example.linkedlearning.views.UIevents
import com.example.linkedlearning.views.auth.signup.SignupViewModel
import com.example.linkedlearning.views.auth.signup.SignupViewModelFactory
import kotlinx.coroutines.launch

class OTPVerifyViewModelFactory(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = OTPVerifyViewModel(context) as T
}

@Composable
fun OTPScreen(
    onNavigate:(to:String)->Unit,
    showSnackBar:(msg:String)->Unit,
    context: Context
){
    val viewModel:OTPVerifyViewModel = viewModel(factory = OTPVerifyViewModelFactory(context))
    val otpValue =  viewModel.otpVal.observeAsState()
    val isResendActive = viewModel.isResendActive.observeAsState()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    //Handling UI events
    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collect{event->

            when(event){
                is UIevents.ShowErrorSnackBar->{
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.msg,
                    )
                }
            }
        }
    }
    Scaffold(scaffoldState = scaffoldState) {
        Column(modifier = Modifier.fillMaxWidth() , horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.linked_learning__logo),
                contentDescription = "Logo",
                modifier = Modifier.size(150.dp)
            )
            Text("Verify OTP" , modifier = Modifier.padding(20.dp) , fontSize = 40.sp)


            OutlinedTextField(value = otpValue.value.toString(), onValueChange = {
                    newText->viewModel.setOTPVal(newText)
            },
                label = { Text(text = "Enter OTP") },
                modifier = Modifier
                    .padding(10.dp)
                    .height(60.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.secondary
                )
            )

            //Button
            Button(onClick = {
                    coroutineScope.launch{
                        if(viewModel.verifyOTPReq()){
                            onNavigate(Routes.LOGIN)
                            showSnackBar("Account verified. Please Login")
                        }
                    }
                } ,
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                modifier = Modifier.padding(10.dp)
            ) {
                Text("Verify" , modifier = Modifier.padding(top = 1.dp , bottom = 1.dp , start = 2.dp , end = 2.dp), fontSize = 18.sp , color = MaterialTheme.colors.onPrimary)
            }

            // Resend Prompt
            if(isResendActive.value == true){
                ClickableText(text = AnnotatedString("Resend OTP"), onClick = {
                    coroutineScope.launch {
                        viewModel.resendOTP()
                    }
                } , modifier = Modifier.padding(10.dp) , style = TextStyle(color = Color.Blue , fontSize = 15.sp) )
            }else{
                TimerComponent(
                    seconds = 100,
                    resetResend = {
                        Log.i("UIMsg" , "Hit")
                        viewModel.setReset(true)
                    }
                )
            }
        }
    }
}