package com.example.linkedlearning.views.courseShow.courseDetails

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.linkedlearning.Utils.Routes
import com.example.linkedlearning.components.LectureCard
import com.example.linkedlearning.components.LoadingScreen
import com.example.linkedlearning.components.Navbar
import com.example.linkedlearning.components.smallBannerAd
import com.example.linkedlearning.data.api.course.data.Course
import com.example.linkedlearning.data.api.course.data.Syllabu
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class CourseDetailsViewModelFactory(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = CourseDetailsViewModel(context) as T

}
@Composable
fun CourseDetailsScreen(
    onNavigate :(to:String)->Unit,
    showSnackBar:(to:String)->Unit,
    context: Context
){
    val viewModel:CourseDetailsViewModel = viewModel(factory = CourseDetailsViewModelFactory(context))
    val coroutineScope = rememberCoroutineScope()
    var loading by remember{ mutableStateOf(true) }
    loading = false
    val courseData = viewModel.courseData.observeAsState(null)
    val currentView by viewModel.currentView.observeAsState();
    LaunchedEffect(key1 = true ){
        viewModel.getCourseData()
    }
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
                    Navbar(onNavigate = {
                        onNavigate(it) } ,  context = context)


                }
            }
        },
    ) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())){
            if(courseData.value == null){
                LoadingScreen()
            }else{
                Text(text = courseData.value!!.title , style = TextStyle(fontSize = 30.sp) , modifier = Modifier.padding(start = 25.dp , top = 20.dp ,end = 25.dp))
                Text(text= courseData.value!!.descp , style= TextStyle(fontSize = 16.sp) , modifier = Modifier.padding(start = 25.dp , top = 20.dp , end = 25.dp))
                Spacer(modifier = Modifier.height(20.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally , modifier = Modifier.fillMaxWidth()) {
                    var rating = 0;
                    Log.i("UIEvent" , courseData.value!!.ratings.size.toString())
                    for(i in 0 until courseData.value!!.ratings.size){
                        Log.i("UIEvent" , i.toString())
                        rating+= courseData.value!!.ratings[i].rate
                    }
                    if(rating > 0){
                        rating /= courseData.value!!.ratings.size
                    }
                    Row() {
                        repeat(rating){
                            Icon(imageVector = Icons.Outlined.Star, contentDescription = null, tint = Color(237, 226, 24))
                        }
                        repeat(5-rating){
                            Icon(imageVector = Icons.Outlined.Star, contentDescription = null, tint = Color(186, 186, 184))
                        }
                    }
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp) , verticalAlignment = Alignment.CenterVertically){
                        Spacer(modifier = Modifier.width(25.dp))
                        Image(
                            painter = rememberImagePainter(
                                data = courseData.value!!.owner.image,
                                builder = {
                                    // You can customize the image loading with options here
                                    // For example, you can set a placeholder or error drawable
                                }
                            ),
                            contentDescription = "Profile picture",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(courseData.value!!.owner.username)
                        Spacer(modifier = Modifier.width(40.dp))
                        Text("${courseData.value!!.EnrollmentCount} enrolled")
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Spacer(modifier = Modifier.height(10.dp))
                }

                Button(onClick =
                {
                    coroutineScope.launch {
                        if(viewModel.enrollIntoCourse(courseData.value!!._id)){
                            showSnackBar("Enrolled")
                        }else{
                            showSnackBar("You are already enrolled")
                        }

                    }
                },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text("Enroll Now" , modifier = Modifier.padding(top = 1.dp , bottom = 1.dp , start = 2.dp , end = 2.dp), fontSize = 15.sp , color = MaterialTheme.colors.onPrimary)
                }


                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ){
                    ClickableText(text = AnnotatedString("Syllabus"), style = TextStyle(fontSize = 20.sp, color=MaterialTheme.colors.secondary), onClick ={viewModel.setCurrentViewModel("SYLLABUS")} )
                    Spacer(modifier = Modifier.width(20.dp))
                    ClickableText(text = AnnotatedString("Lectures"), style = TextStyle(fontSize = 20.sp , color=MaterialTheme.colors.secondary), onClick ={
                        Log.i("UIEvent" , "Clicked")
                        viewModel.setCurrentViewModel("LECTURES")} )
                    Spacer(modifier = Modifier.width(20.dp))
                    ClickableText(text = AnnotatedString("Discussion"), style = TextStyle(fontSize = 20.sp , color=MaterialTheme.colors.secondary), onClick ={
                        Log.i("UIEvent" , "Clicked")
                        viewModel.setCurrentViewModel("DISCUSSION")} )
                }

                Log.i("UIEvent" , viewModel.currentView.value!!)

                if(currentView == "SYLLABUS"){
                    val syllabusCopy:List<Syllabu> = courseData.value!!.syllabus
                    for (i in 0..(syllabusCopy.size - 1)){
                        Text(syllabusCopy[i].title , style = TextStyle(fontSize = 25.sp) , modifier = Modifier.padding(20.dp))
                        var topics = syllabusCopy[i].subTopics.split(",")
                        Log.i("UIEvent" , topics.toString())
                        for(j in 0..(topics.size - 1)){
                            Text(topics[j] , modifier = Modifier.padding(start = 20.dp , top = 10.dp , bottom = 10.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(100.dp))
                }else if (currentView == "LECTURES"){
                    Spacer(modifier = Modifier.height(20.dp))
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp) , horizontalAlignment = Alignment.CenterHorizontally){
                        for(i in 0..(courseData.value!!.content.size - 1)){
                            Text(courseData.value!!.content[i].title , fontSize = 22.sp)
                            for(j in 0..(courseData.value!!.content[i].secContent.size - 1)){
                                Spacer(modifier = Modifier.height(10.dp))
                                LectureCard(courseData.value!!.content[i].secContent[j] , onClick = {
                                    val webIntent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(it)
                                    )
                                    context.startActivity(webIntent)
                                })
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                        Text("Rate the course" , style=TextStyle(fontSize = 20.sp))
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(onClick = {
                                coroutineScope.launch {
                                    if(viewModel.rateCourse(5)){
                                        showSnackBar("Thankyou for the rating")
                                    }else{
                                        showSnackBar("You already rated this course")}
                                }
                            } , modifier = Modifier.padding(10.dp).width(40.dp) , colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)) {
                                Text("5")
                            }
                            Button(onClick = {  coroutineScope.launch {
                                if(viewModel.rateCourse(4)){
                                    showSnackBar("Thankyou for the rating")
                                }else{
                                    showSnackBar("You already rated this course")}
                            } } , modifier = Modifier.padding(10.dp).width(40.dp) , colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)) {
                                Text("4")
                            }
                            Button(onClick = {  coroutineScope.launch {
                                Log.i("APIEvent" , "Before sending")
                                if(viewModel.rateCourse(3)){
                                    showSnackBar("Thankyou for the rating")
                                }else{
                                    showSnackBar("You already rated this course")}
                            } } , modifier = Modifier.padding(10.dp).width(40.dp) , colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)) {
                                Text("3")
                            }
                            Button(onClick = {  coroutineScope.launch {
                                if(viewModel.rateCourse(2)){
                                    showSnackBar("Thankyou for the rating")
                                }else{
                                    showSnackBar("You already rated this course")}
                            } } , modifier = Modifier.padding(10.dp).width(40.dp) , colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)) {
                                Text("2")
                            }
                            Button(onClick = {  coroutineScope.launch {
                                if(viewModel.rateCourse(1)){
                                    showSnackBar("Thankyou for the rating")
                                }else{
                                    showSnackBar("You already rated this course")}
                            } } , modifier = Modifier.padding(10.dp).width(40.dp) , colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)) {
                                Text("1")
                            }
                        }
                    }
                }else{
                    Column(modifier = Modifier.fillMaxWidth()) {
                        runBlocking { viewModel.getQuestions() }
                        val questions = viewModel.questions;
                        Log.i("APIEvent" , questions.value.toString())
                        Row(horizontalArrangement = Arrangement.End){
                            Button(onClick =
                            {
                                onNavigate(Routes.NEWQUESTION)
                            },
                                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                                modifier = Modifier.padding(25.dp)
                            ) {
                                Text("Ask a question" , modifier = Modifier.padding(top = 1.dp , bottom = 1.dp , start = 1.dp , end = 1.dp), fontSize = 12.sp , color = MaterialTheme.colors.onPrimary)
                            }
                        }

                        Log.i("APIEvent" , questions.value.toString())
                        for(i in 0..(questions.value!!.size - 1)){
                            Column(modifier = Modifier
                                .fillMaxWidth()
                                .padding(25.dp)
                                .clickable {
                                    coroutineScope.launch {
                                        viewModel.setLectureId(questions.value!![i]._id)
                                        onNavigate(Routes.SHOWQUESTION)
                                    }
                                }){
                                Log.i("APIEvent" , questions.value.toString())
                                Text(questions.value!![i].title , style= TextStyle(fontSize = 25.sp))

                                if(questions.value!![i].descp != null){
                                    Text(questions.value!![i].descp!!)
                                }
                            }
                        }

                    }
                }
            }

        }
    }
}