package com.example.linkedlearning.views.auth.login
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.linkedlearning.MainActivity
import com.example.linkedlearning.R
import com.example.linkedlearning.Utils.Routes
import com.example.linkedlearning.components.LoadingScreen
import com.example.linkedlearning.data.authData.AuthRepo
import com.example.linkedlearning.views.UIevents
import com.example.linkedlearning.views.auth.signup.SignupViewModel
import com.example.linkedlearning.views.auth.signup.SignupViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModelFactory(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = LoginViewModel(context) as T
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LoginScreen(
    onNavigate: (to:String)-> Unit,
    showSnackBar:(to:String)->Unit,
    context: Context
){
    val viewModel:LoginViewModel = viewModel(factory = LoginViewModelFactory(context))
    val coroutineScope = rememberCoroutineScope()
    var key by remember { mutableStateOf(0) }
    var loading by remember { mutableStateOf(false) }
//    loading = true;
    LaunchedEffect(key1 = true){
        if(viewModel.getLoginStatus() == true) {
            onNavigate(Routes.DASHBOARD)
        }
//        }else{
//            Log.i("APIEvent" , "setting loading false")
//            loading = false
//        }
    }
    val email = viewModel.email.observeAsState()
    val password = viewModel.password.observeAsState()
    val scaffoldState = rememberScaffoldState()


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
//            if(loading){
//                LoadingScreen()
//            }else{
                Image(
                    painter = painterResource(id = R.drawable.linked_learning__logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(150.dp)
                )
                Text("Login" , modifier = Modifier.padding(20.dp) , fontSize = 40.sp)
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

                //Button
                Button(onClick = {
                    coroutineScope.launch {
                        //loading = true;
                        if(viewModel.login()){
                            showSnackBar("Logged in successfully")
                            onNavigate(Routes.DASHBOARD)
                        }
                    }
                } ,
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text("Login" , modifier = Modifier.padding(top = 1.dp , bottom = 1.dp , start = 2.dp , end = 2.dp), fontSize = 18.sp , color = MaterialTheme.colors.onPrimary)
                }

                // Signup Prompt
                ClickableText(text = AnnotatedString("Don't have an account? Signup"), onClick = {
                    onNavigate(Routes.SIGNUP)
                } , modifier = Modifier.padding(10.dp) , style = TextStyle(color = Blue , fontSize = 15.sp) )
            }
//        }
    }
}