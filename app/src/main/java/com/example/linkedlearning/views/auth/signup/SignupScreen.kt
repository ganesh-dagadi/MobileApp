package com.example.linkedlearning.views.auth.signup

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.linkedlearning.R
import com.example.linkedlearning.Utils.Routes
import com.example.linkedlearning.views.UIevents
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(
    onNavigate : (to:String)->Unit
){
    val viewModel = viewModel<SignupViewModel>()
    val email = viewModel.email.observeAsState()
    val password = viewModel.password.observeAsState()
    val confirmPassword = viewModel.confirmPassword.observeAsState()
    val isChecked = viewModel.isChecked.observeAsState()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    //Handling UI events
    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collect{event->
            Log.i("errorMsg" , "In hereeeee")
            when(event){
                is UIevents.ShowErrorSnackBar->{
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.msg,

                    )
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .fillMaxWidth() , horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.linked_learning__logo),
                contentDescription = "Logo",
                modifier = Modifier.size(150.dp)
            )
            Text("Signup" , modifier = Modifier.padding(20.dp) , fontSize = 40.sp)
            OutlinedTextField(value = email.value.toString(), onValueChange = {
                    newText->viewModel.setEmailText(newText)
            },
                label = { Text(text = "Email") },
                modifier = Modifier
                    .padding(10.dp)
                    .height(60.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.secondary
                )
            )
            OutlinedTextField(value = password.value.toString(), onValueChange = {
                    newText->viewModel.setPasswordText(newText)
            },
                label = { Text(text = "Password") },
                modifier = Modifier
                    .padding(10.dp)
                    .height(60.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.secondary
                ),
                visualTransformation = PasswordVisualTransformation()
            )
            //Confirm Password
            OutlinedTextField(value = confirmPassword.value.toString(), onValueChange = {
                    newText->viewModel.setConfirmPassword(newText)
            },
                label = { Text(text = "Confirm Password") },
                modifier = Modifier
                    .padding(10.dp)
                    .height(60.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.secondary
                ),
                visualTransformation = PasswordVisualTransformation()
            )
            //Button
            Button(onClick = {
                 coroutineScope.launch {
                     viewModel.signupUser()
                 }
            } ,
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                modifier = Modifier.padding(10.dp)
            ) {
                Text("Signup" , modifier = Modifier.padding(top = 1.dp , bottom = 1.dp , start = 2.dp , end = 2.dp), fontSize = 18.sp , color = MaterialTheme.colors.onPrimary)
            }

            Row (
                horizontalArrangement = Arrangement.Center
            ){
                Checkbox(
                    // below line we are setting
                    // the state of checkbox.
                    checked = isChecked.value!!,
                    // below line is use to add padding
                    // to our checkbox.
                    modifier = Modifier.padding(start = 16.dp),
                    // below line is use to add on check
                    // change to our checkbox.
                    onCheckedChange = { viewModel.toggleIsChecked() },
                )
                // below line is use to add text to our check box and we are
                // adding padding to our text of checkbox
                Text(text = "I agree to Terms and condition and Privacy policy", modifier = Modifier.padding(start= 2.dp))
            }
            // Signup Prompt
            ClickableText(text = AnnotatedString("Already have an account? Login"), onClick = {
                onNavigate(Routes.LOGIN)
            } , modifier = Modifier.padding(10.dp) , style = TextStyle(color = Color.Blue , fontSize = 15.sp) )

        }
    }
}