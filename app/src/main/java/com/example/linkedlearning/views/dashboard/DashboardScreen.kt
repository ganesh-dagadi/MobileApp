package com.example.linkedlearning.views.dashboard

import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.linkedlearning.Utils.Routes
import com.example.linkedlearning.data.api.ApiCore
import com.example.linkedlearning.data.api.auth.AuthAPI
import com.example.linkedlearning.data.authData.AuthRepo
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen(
    onNavigate : ()->Unit,
    context: Context
){
    val coroutineScope = rememberCoroutineScope()
    val repoInstance = AuthRepo(context)
    val retrofitInstance = ApiCore(context).getInstance().create(AuthAPI::class.java)

    Scaffold(scaffoldState = rememberScaffoldState()) {
        Column{
            Text(text = "Dashboard")

            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                modifier = Modifier
                    .padding(10.dp),
                onClick = {
                    Log.i("UIEvent" , "Hey babe")
                    val repoInstance = AuthRepo(context)
                    coroutineScope.launch {
                        repoInstance.clearSf()
                    }
                }
            ) {
                Text("Login" , modifier = Modifier.padding(top = 1.dp , bottom = 1.dp , start = 2.dp , end = 2.dp), fontSize = 18.sp , color = MaterialTheme.colors.onPrimary)
            }

            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                modifier = Modifier
                    .padding(10.dp),
                onClick = {
                    Log.i("APIEvent" , "Calling protected")
                    coroutineScope.launch {
                        val response = retrofitInstance.getProtected();
                        Log.i("ApiEvent" , response.body()!!.msg)
                    }
                }
            ) {
                Text("Access protected" , modifier = Modifier.padding(top = 1.dp , bottom = 1.dp , start = 2.dp , end = 2.dp), fontSize = 18.sp , color = MaterialTheme.colors.onPrimary)
            }
        }

    }
}