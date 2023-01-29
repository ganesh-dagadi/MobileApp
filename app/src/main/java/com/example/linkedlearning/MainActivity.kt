package com.example.linkedlearning


import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.linkedlearning.Utils.Routes
import com.example.linkedlearning.ui.theme.LinkedLearningTheme
import com.example.linkedlearning.views.auth.OTPverify.OTPScreen
import com.example.linkedlearning.views.dashboard.DashboardScreen
import com.example.linkedlearning.views.auth.login.LoginScreen
import com.example.linkedlearning.views.auth.signup.SignupScreen
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var instance:MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context: Context = this
        setContent {
            LinkedLearningTheme {
                val navController = rememberNavController();
                val scaffoldState = rememberScaffoldState()
                Scaffold(scaffoldState = scaffoldState){
                    NavHost(navController = navController, startDestination = Routes.LOGIN){
                        composable(Routes.LOGIN){
                            LoginScreen (
                                onNavigate = {
                                    navController.popBackStack()
                                    navController.navigate(it)
                                },
                                context = context,
                                showSnackBar = {
                                    GlobalScope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(it)
                                    }
                                }
                            )
                        }
                        composable(Routes.DASHBOARD){
                            DashboardScreen(
                                onNavigate = {navController.navigate(Routes.LOGIN)},
                                context = context
                            )
                        }
                        composable(Routes.SIGNUP){
                            SignupScreen(
                                onNavigate = {
                                    navController.popBackStack()
                                    navController.navigate(it)
                                },
                                context = context,
                                showSnackBar = {
                                    GlobalScope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(it)
                                    }
                                }
                            )
                        }
                        composable(Routes.OTPVERIFY){
                            OTPScreen(
                                onNavigate ={
                                    navController.popBackStack()
                                    navController.navigate(it)
                                },
                                context = context,
                                showSnackBar = {
                                    GlobalScope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(it)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
    fun getInstance():MainActivity{
        return instance
    }
}
