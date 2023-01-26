package com.example.linkedlearning


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.linkedlearning.Utils.Routes
import com.example.linkedlearning.ui.theme.LinkedLearningTheme
import com.example.linkedlearning.views.dashboard.DashboardScreen
import com.example.linkedlearning.views.login.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LinkedLearningTheme {
               val navController = rememberNavController();
               NavHost(navController = navController, startDestination = Routes.LOGIN){
               composable(Routes.LOGIN){
                LoginScreen (
                    onNavigate = {
                        navController.popBackStack()
                        navController.navigate(it)
                    }
                )
               }
               composable(Routes.DASHBOARD){
                   DashboardScreen(
                       onNavigate = {navController.navigate(Routes.LOGIN)}
                   )
               }
                }
            }
        }
    }
}
