package com.example.linkedlearning.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.linkedlearning.Utils.Routes
import com.example.linkedlearning.data.api.ApiCore
import com.example.linkedlearning.data.api.auth.AuthAPI
import com.example.linkedlearning.data.api.auth.data.SignupResponse
import com.example.linkedlearning.data.api.course.CourseAPI
import com.example.linkedlearning.data.authData.AuthRepo
import com.example.linkedlearning.data.courseData.CourseRepo
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

@Composable
fun Navbar(context: Context, onNavigate:(to:String)->Unit){
    val retrofitInstance = ApiCore(context).getInstance().create(AuthAPI::class.java)
    val coroutineScope = rememberCoroutineScope()
    Column(Modifier.clickable { onNavigate(Routes.USERPROFILE) } , horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(Icons.Filled.Person , contentDescription = "Profile icon" , tint = Color.White)
        Text("Profile" , style = TextStyle(color = Color.White))
    }
    Column(Modifier.clickable { onNavigate(Routes.DASHBOARD) } , horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(Icons.Filled.Home, contentDescription = "Home Icon" , tint = Color.White)
        Text("Home" , style = TextStyle(color = Color.White))
    }
    Column(Modifier.clickable { coroutineScope.launch {

        val repoInstance = AuthRepo(context)
        val refreshToken ="Bearer "+ runBlocking{repoInstance.getRefreshToken()}
        val mediaType = "application/json".toMediaTypeOrNull()
        val requestBody = "{\"key\":\"value\"}".toRequestBody(mediaType)

        val refreshRequest = Request.Builder().url("https://api-linkedlearning.onrender.com/auth/logout")
            .addHeader("Authorization" , refreshToken)
            .patch(requestBody)
            .build()
        val refreshClient = OkHttpClient()
        var refreshResponse:Response
        Log.i("APIEvent" , "Before logout start")
        coroutineScope.launch(Dispatchers.IO) {
                refreshResponse = refreshClient.newCall(refreshRequest).execute()
                Log.i("APIEvent" , refreshResponse.code.toString())
                if(refreshResponse.isSuccessful){
                    Log.i("APIEvent" , "In 200")
                    try{
                        repoInstance.clearSf()
                        Log.i("APIEvent" , "cleared sf")
                        withContext(Dispatchers.Main) {
                            onNavigate(Routes.LOGIN)
                        }
                    }catch(e:Exception){
                        Log.i("APIEvent" , e.toString())
                    }
                }
            }
//        val response = retrofitInstance.logoutUser()
//        Log.i("APIEvent" , "Returned from api")
//        Log.i("APIEvent" , response.body().toString())
//        if(response.code() == 200){
//            Log.i("APIEvent" , "In 200")
//            val repoInstance = AuthRepo(context)
//            repoInstance.clearSf()
//            Log.i("APIEvent" , "cleared sf")
//            onNavigate(Routes.LOGIN)
//        }

    } } , horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(Icons.Filled.Logout ,  contentDescription = "Logout Icon" , tint = Color.White)
        Text("Logout" ,style = TextStyle(color = Color.White))
    }
}