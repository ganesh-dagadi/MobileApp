package com.example.linkedlearning.views.dashboard

import android.content.Context
import android.text.style.ClickableSpan
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.linkedlearning.Utils.Routes
import com.example.linkedlearning.data.api.ApiCore
import com.example.linkedlearning.data.api.auth.AuthAPI
import com.example.linkedlearning.data.authData.AuthRepo
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen(
    onNavigate : (to:String)->Unit,
    context: Context
){
    val coroutineScope = rememberCoroutineScope()
    val repoInstance = AuthRepo(context)
    val retrofitInstance = ApiCore(context).getInstance().create(AuthAPI::class.java)

    Scaffold(
        scaffoldState = rememberScaffoldState(),
        bottomBar = {
            BottomNavigation (backgroundColor = MaterialTheme.colors.secondary , modifier = Modifier.border(1.dp , MaterialTheme.colors.secondary)){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)

                ) {
                    Column(Modifier.clickable { onNavigate(Routes.LOGIN) } , horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.Article , contentDescription = "Article icon" , tint = Color.White)
                        Text("Courses" , style = TextStyle(color = Color.White))
                    }
                    Column(Modifier.clickable { onNavigate(Routes.LOGIN) } , horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.Person, contentDescription = "Article icon" , tint = Color.White)
                        Text("Profile" , style = TextStyle(color = Color.White))
                    }
                    Column(Modifier.clickable { onNavigate(Routes.SIGNUP) } , horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.Logout ,  contentDescription = "Article icon" , tint = Color.White)
                        Text("Logout" ,style = TextStyle(color = Color.White))
                    }
                }
            }
        },
    ) {
    }
}