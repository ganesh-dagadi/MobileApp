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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.linkedlearning.data.api.course.data.Syllabu
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
    runBlocking {
       viewModel.getCourseData()
    }
    val courseData = viewModel.courseData.value!!
    val currentView by viewModel.currentView.observeAsState();
    val coroutineScope = rememberCoroutineScope()
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
        Column(modifier = Modifier.verticalScroll(rememberScrollState())){
            Text(text = courseData.title , style = TextStyle(fontSize = 30.sp) , modifier = Modifier.padding(start = 25.dp , top = 20.dp ,end = 25.dp))
            Text(text= courseData.descp , style= TextStyle(fontSize = 16.sp) , modifier = Modifier.padding(start = 25.dp , top = 20.dp , end = 25.dp))
            Spacer(modifier = Modifier.height(20.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally , modifier = Modifier.fillMaxWidth()) {
                Row() {
                    repeat(4){
                        Icon(imageVector = Icons.Outlined.Star, contentDescription = null, tint = Color(237, 226, 24))
                    }
                    repeat(1){
                        Icon(imageVector = Icons.Outlined.Star, contentDescription = null, tint = Color(186, 186, 184))
                    }
                }
                Text("4.2")
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp) , verticalAlignment = Alignment.CenterVertically){
                    Spacer(modifier = Modifier.width(25.dp))
                    Image(
                        painter = rememberImagePainter(
                            data = "https://res.cloudinary.com/dxm68x3tm/image/upload/w_40,h_40,c_scale/v1676117917/linkedLearning/ltomfva5vu19ma9xnsh2.png",
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
                    Text("Course creator")
                    Spacer(modifier = Modifier.width(40.dp))
                    Text("${courseData.EnrollmentCount} enrolled")
                }
            }

            Button(onClick =
            {
                coroutineScope.launch {
                    if(viewModel.enrollIntoCourse(courseData._id)){
                        showSnackBar("Enrolled")
                    }else{
                        showSnackBar("You are already enrolled")
                    }

                }
            },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                modifier = Modifier.padding(25.dp)
            ) {
                Text("Enroll Now" , modifier = Modifier.padding(top = 1.dp , bottom = 1.dp , start = 2.dp , end = 2.dp), fontSize = 15.sp , color = MaterialTheme.colors.onPrimary)
            }
            
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ){
                ClickableText(text = AnnotatedString("Syllabus"), onClick ={viewModel.setCurrentViewModel("SYLLABUS")} )
                Spacer(modifier = Modifier.width(20.dp))
                ClickableText(text = AnnotatedString("Lectures"), onClick ={
                    Log.i("UIEvent" , "Clicked")
                    viewModel.setCurrentViewModel("LECTURES")} )
                Spacer(modifier = Modifier.width(20.dp))
                ClickableText(text = AnnotatedString("Discussion"), onClick ={
                    Log.i("UIEvent" , "Clicked")
                    viewModel.setCurrentViewModel("DISCUSSION")} )
            }

            Log.i("UIEvent" , viewModel.currentView.value!!)

            if(currentView == "SYLLABUS"){
                Log.i("UIEvent" , "Reached in here")
                val syllabusCopy:List<Syllabu> = courseData.syllabus
                for (i in 0..(syllabusCopy.size - 1)){
                    Text(syllabusCopy[i].title , style = TextStyle(fontSize = 25.sp) , modifier = Modifier.padding(20.dp))
                    var topics = syllabusCopy[i].subTopics.split(",")
                    Log.i("UIEvent" , topics.toString())
                    for(j in 0..(topics.size - 1)){
                        Text(topics[j] , modifier = Modifier.padding(10.dp))
                    }
                }
            }else if (currentView == "LECTURES"){
                Spacer(modifier = Modifier.height(20.dp))
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp) , horizontalAlignment = Alignment.CenterHorizontally){
                    for(i in 0..(courseData.content.size - 1)){
                        Text(courseData.content[i].title , fontSize = 22.sp)
                        for(j in 0..(courseData.content[i].secContent.size - 1)){
                            Spacer(modifier = Modifier.height(10.dp))
                            LectureCard(courseData.content[i].secContent[j] , onClick = {
                                val webIntent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(it)
                                )
                                context.startActivity(webIntent)
                            })
                            Spacer(modifier = Modifier.height(10.dp))
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
                        Column(modifier = Modifier.fillMaxWidth().padding(25.dp).clickable {

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