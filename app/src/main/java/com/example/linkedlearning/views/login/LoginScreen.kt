package com.example.linkedlearning.views.login
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.linkedlearning.MainActivity
import com.example.linkedlearning.Utils.Routes

@Composable
fun LoginScreen(
    onNavigate: (to:String)-> Unit
){
    val myViewModel = viewModel<LoginViewModel>()
    val username = myViewModel.username.observeAsState()
    Text(username.value.toString())
}