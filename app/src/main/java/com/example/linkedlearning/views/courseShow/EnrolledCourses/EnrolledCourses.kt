package com.example.linkedlearning.views.courseShow.EnrolledCourses

import android.content.Context
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.linkedlearning.Utils.Routes
import com.example.linkedlearning.components.BannerAd
import com.example.linkedlearning.components.CourseCard
import kotlinx.coroutines.runBlocking

class EnrolledCoursesScreenViewModelFactory(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = EnrolledCoursesViewModel(context) as T
}

@Composable
fun EnrolledCoursesScreen(
    onNavigate:(to:String)->Unit,
    context:Context
){
    val viewModel:EnrolledCoursesViewModel = viewModel(factory = EnrolledCoursesScreenViewModelFactory(context))
    runBlocking { viewModel.getEnrolledCourses() }
    val enrolledCourses = viewModel.enrolledCoursesList.value

    Scaffold(scaffoldState = rememberScaffoldState(),
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
                        Icon(Icons.Filled.Person, contentDescription = "Person Icon" , tint = Color.White)
                        Text("Profile" , style = TextStyle(color = Color.White))
                    }
                    Column(Modifier.clickable { onNavigate(Routes.SIGNUP) } , horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.Logout ,  contentDescription = "Logout Icon" , tint = Color.White)
                        Text("Logout" ,style = TextStyle(color = Color.White))
                    }
                }
            }
        },
    ) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Text("Enrolled Courses" , modifier = Modifier.padding(10.dp), style = TextStyle(
                fontSize = 30.sp
            ))

            BannerAd()

            for(i in 0..(enrolledCourses!!.size - 1)){
                CourseCard(enrolledCourses!![i] , onCardClick = {

                    runBlocking {
                        viewModel.setSelectedCourseId(enrolledCourses[i]._id)
                    }
                    onNavigate(Routes.COURSEDETAILS)
                })
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

