package com.example.linkedlearning.views.auth.OTPverify

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.linkedlearning.R
import com.example.linkedlearning.Utils.Routes

@Composable
fun OTPScreen(
    onNavigate:(to:String)->Unit
){
    val viewModel = viewModel<OTPVerifyViewModel>()
    val otpValue =  viewModel.otpVal.observeAsState()
    
    Scaffold() {
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
            Button(onClick = { /*TODO*/ } ,
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                modifier = Modifier.padding(10.dp)
            ) {
                Text("Verify" , modifier = Modifier.padding(top = 1.dp , bottom = 1.dp , start = 2.dp , end = 2.dp), fontSize = 18.sp , color = MaterialTheme.colors.onPrimary)
            }

            // Resend Prompt
            ClickableText(text = AnnotatedString("Resend OTP"), onClick = {
                onNavigate(Routes.SIGNUP)
            } , modifier = Modifier.padding(10.dp) , style = TextStyle(color = Color.Blue , fontSize = 15.sp) )
        }
    }
}