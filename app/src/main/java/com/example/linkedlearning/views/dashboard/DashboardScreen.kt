package com.example.linkedlearning.views.dashboard

import android.content.Context
import android.text.style.ClickableSpan
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.linkedlearning.Utils.Routes
import com.example.linkedlearning.components.BannerAd
import com.example.linkedlearning.components.CourseCard
import com.example.linkedlearning.components.SearchBar
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DashboardScreenViewModelFactory(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = DashBoardViewModel(context) as T
}
@Composable
fun DashboardScreen(
    onNavigate : (to:String)->Unit,
    context: Context
){
    val viewModel:DashBoardViewModel = viewModel(factory = DashboardScreenViewModelFactory(context))
    runBlocking {viewModel.getAllCourses() ; viewModel.getEnrolledCourses(); viewModel.getCategories()}
    val coroutineScope = rememberCoroutineScope()
    val courses by viewModel.coursesList.observeAsState()
    val categories = viewModel.categoryList.value
    val enrolledCourses = viewModel.enrolledCoursesList.value
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
                    Column(Modifier.clickable { onNavigate(Routes.USERPROFILE) } , horizontalAlignment = Alignment.CenterHorizontally) {
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
            Row(horizontalArrangement = Arrangement.Center){
                SearchBar(
                    enteredText = {
                        Log.i("UIEvent" ,it)
                    }
                )
            }

            BannerAd()

            // Search by category
            Text("Search by Category" , modifier = Modifier.padding(10.dp), style = TextStyle(
                fontSize = 30.sp
            ))

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                item{
                    Spacer(modifier = Modifier.width(4.dp))
                }
                items(categories!!.size) { index ->
                    Spacer(modifier = Modifier.width(6.dp))
                    ClickableText(text = AnnotatedString(text = categories[index].title) ,modifier = Modifier
                        .background(
                            Color.LightGray, shape = RoundedCornerShape(
                                CornerSize(5.dp)
                            )
                        )
                        .padding(start = 5.dp, end = 5.dp), onClick = {
                        coroutineScope.launch {
                            viewModel.getCoursesByCategory(categories[index]._id)
                        }
                    })
                    Spacer(modifier = Modifier.width(3.dp))
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(10.dp))
            Text("Currently Learning" , style = TextStyle(fontSize = 30.sp) , modifier = Modifier.padding(start = 10.dp))

            Spacer(modifier = Modifier.height(30.dp))
            if(courses!!.isNotEmpty()){

            }
            CourseCard(enrolledCourses!![0] , onCardClick = {
                runBlocking {
                    viewModel.setSelectedCourseId(enrolledCourses[0]._id)
                }
                onNavigate(Routes.COURSEDETAILS)
            })
            Spacer(modifier = Modifier.height(30.dp))
            CourseCard(enrolledCourses!![1], onCardClick = {
                runBlocking {
                    viewModel.setSelectedCourseId(enrolledCourses[1]._id)
                }
                onNavigate(Routes.COURSEDETAILS)
            })
            Spacer(modifier = Modifier.height(30.dp))
            Row(horizontalArrangement = Arrangement.Center , modifier = Modifier.fillMaxWidth()){
                ClickableText(text = AnnotatedString(text = "View all") , onClick = {
                    onNavigate(Routes.ENROLLEDCOURSES)
                })
            }
            Spacer(modifier = Modifier.height(30.dp))

            Text("Explore courses" , style = TextStyle(fontSize = 30.sp) , modifier = Modifier.padding(start = 10.dp))
            for(i in 0..(courses!!.size - 1)){
                CourseCard(courses!![i] , onCardClick = {
                    runBlocking {
                        viewModel.setSelectedCourseId(courses!![i]._id)
                    }
                    onNavigate(Routes.COURSEDETAILS)
                })
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

