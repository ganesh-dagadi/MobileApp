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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.linkedlearning.components.LoadingScreen
import com.example.linkedlearning.components.Navbar
import com.example.linkedlearning.data.api.course.data.Course
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
    val enrolledCourses = viewModel.enrolledCoursesList.observeAsState(List<Course?>(10){null}).value
    LaunchedEffect(key1 = true){
        viewModel.getEnrolledCourses()
    }

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
                    Navbar(onNavigate = {
                        onNavigate(it) } ,  context = context)
                }
            }
        },
    ) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            if(enrolledCourses[0] == null){
                LoadingScreen()
            }else{
                Text("Enrolled Courses" , modifier = Modifier.padding(10.dp), style = TextStyle(
                    fontSize = 30.sp
                ))

                BannerAd(context)

                for(i in 0 until enrolledCourses.size){
                    CourseCard(enrolledCourses[i]!! , onCardClick = {

                        runBlocking {
                            viewModel.setSelectedCourseId(enrolledCourses[i]!!._id)
                        }
                        onNavigate(Routes.COURSEDETAILS)
                    })
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }

        }
    }
}

