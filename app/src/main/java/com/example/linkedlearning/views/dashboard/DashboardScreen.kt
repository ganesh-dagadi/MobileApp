package com.example.linkedlearning.views.dashboard

import android.content.Context
import android.text.style.ClickableSpan
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.linkedlearning.Utils.Routes
import com.example.linkedlearning.components.BannerAd
import com.example.linkedlearning.components.SearchBar
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
    runBlocking { viewModel.getAllCourses() ; viewModel.getCategories()}
    val courses = viewModel.coursesList.value
    val categories = viewModel.categoryList.value
    Log.i("APIEvent" , categories.toString())
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
        Column {
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
                    .height(200.dp)
            ){
                item{
                    Spacer(modifier = Modifier.width(4.dp))
                }
                items(categories!!.size) { index ->
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(categories[index].title)
                    Spacer(modifier = Modifier.width(6.dp))
                }
            }
        }
    }
}

