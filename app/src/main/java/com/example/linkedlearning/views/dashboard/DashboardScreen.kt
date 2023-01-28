package com.example.linkedlearning.views.dashboard

import android.content.Context
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun DashboardScreen(
    onNavigate : ()->Unit,
    context: Context
){
    Text(text = "Dashboard")
}