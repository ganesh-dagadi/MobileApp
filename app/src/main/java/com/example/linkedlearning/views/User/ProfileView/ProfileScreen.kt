package com.example.linkedlearning.views.User.ProfileView

import android.content.Context
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.linkedlearning.Utils.Routes
import com.example.linkedlearning.components.BannerAd
import com.example.linkedlearning.components.CourseCard
import com.example.linkedlearning.components.LoadingScreen
import com.example.linkedlearning.components.Navbar
import com.example.linkedlearning.data.api.course.data.Course
import com.example.linkedlearning.views.dashboard.DashBoardViewModel
import com.example.linkedlearning.views.dashboard.DashboardScreenViewModelFactory
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileScreenViewModelFactory(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = ProfileViewModel(context) as T
}

@Composable
fun dpToPx(dp: Dp): Int {
    val density = LocalDensity.current
    return density.run { dp.toPx() }.toInt()
}
@Composable
fun ProfileScreen(
    onNavigate : (to:String)->Unit,
    context: Context,
    showSnackBar:(to:String)->Unit,
){
    val viewModel:ProfileViewModel = viewModel(factory = ProfileScreenViewModelFactory(context))
    val username = viewModel.username.observeAsState(null);
    val email = viewModel.email.observeAsState(null);
    val imageUrl = viewModel.imageUrl.observeAsState(null);
    val courses = viewModel.courses.observeAsState(List<Course?>(10){null}).value
//    runBlocking { viewModel.getUserData() ; viewModel.getCreatedCourses()}
    LaunchedEffect(key1 = true){
        viewModel.getUserData() ; viewModel.getCreatedCourses()
    }
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp
    var screenWidthPx = com.example.linkedlearning.components.dpToPx(screenWidth)
    screenWidthPx -= com.example.linkedlearning.components.dpToPx(dp = 200.dp)
    val heightImgPx= com.example.linkedlearning.components.dpToPx(150.dp)

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
                        onNavigate(it) } , context = context)
                    }


            }
        }
    ){
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            if(username.value == null || email.value == null || imageUrl.value == null || courses!![0] == null){
                LoadingScreen()
            }else{
                val newImageString = viewModel.imageUrl.value!!.substring(0, 49)+ "/w_$screenWidthPx,h_$heightImgPx,c_scale" + viewModel.imageUrl.value!!.substring(49)
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                ) {

                    Image(
                        // on below line we are adding the image url
                        // from which we will  be loading our image.

                        painter = rememberAsyncImagePainter(newImageString),

                        // on below line we are adding content
                        // description for our image.
                        contentDescription = "profile image",

                        // on below line we are adding modifier for our
                        // image as wrap content for height and width.

                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    )

                    IconButton(
                        onClick = {
                            showSnackBar("Visit the web app to edit profile picture")
                        },
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.BottomEnd)
                            .padding(16.dp),
                        content = {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Icon"
                            )
                        }
                    )

                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(viewModel.username.value!! , textAlign = TextAlign.Center,style = TextStyle(fontSize = 30.sp) , modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(5.dp))
                Text(viewModel.email.value!! , textAlign = TextAlign.Center,style = TextStyle(fontSize = 20.sp) , modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(20.dp))
                BannerAd(context)
                Spacer(modifier = Modifier.height(5.dp))
                Text("Courses Created" , style=TextStyle(fontSize = 30.sp) , modifier = Modifier.padding(10.dp))
                Text("Visit the web app on desktop to modify your courses" , modifier = Modifier.padding(10.dp));
                for(i in 0 until  courses.size){
                    CourseCard(courses[i]!! , onCardClick = {
                        runBlocking {
                            viewModel.setSelectedCourseId(courses[i]!!._id)
                        }
                        onNavigate(Routes.COURSEDETAILS)
                    })
                }

            }
            }

    }
}