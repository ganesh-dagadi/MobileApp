package com.example.linkedlearning.views.courseShow.NewQuestion

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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.linkedlearning.views.auth.login.LoginViewModel
import com.example.linkedlearning.views.auth.login.LoginViewModelFactory
import kotlinx.coroutines.launch

class NewQuestionScreenViewModelFactory(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = NewQuestionViewModel(context) as T
}

@Composable
fun NewQuestionScreen(
    context:Context,
    onNavigate:(to:String)->Unit,
    showSnackBar:(to:String)->Unit
){
    val viewModel: NewQuestionViewModel = viewModel(factory = NewQuestionScreenViewModelFactory(context))
    val title = viewModel.title.observeAsState()
    val descp = viewModel.descp.observeAsState()
    val coroutinesScope = rememberCoroutineScope()
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
    ){
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth() , horizontalAlignment = Alignment.CenterHorizontally) {
            Text("New Question" , style= TextStyle(fontSize = 30.sp))
            OutlinedTextField(value = title.value.toString(), onValueChange = {
                    newText->viewModel.setTitleText(newText)
            },
                label = { Text(text = "Title") },
                modifier = Modifier
                    .padding(10.dp)
                    .height(60.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.secondary
                )
            )
            OutlinedTextField(value = descp.value.toString(), onValueChange = {
                    newText->viewModel.setDescpText(newText)
            },
                label = { Text(text = "Details") },
                modifier = Modifier
                    .padding(10.dp)
                    .height(300.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.secondary
                )
            )

            Button(onClick = {
                coroutinesScope.launch {
                    if(viewModel.postNewQuestion()){
                        Log.i("APIEvent" , "Entered here you dumbo")
                        showSnackBar("Question created")
                    }else{
                        showSnackBar("Something went wrong")
                    }
                }

            } ,
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                modifier = Modifier.padding(10.dp)
            ) {
                Text("Submit" , modifier = Modifier.padding(top = 1.dp , bottom = 1.dp , start = 2.dp , end = 2.dp), fontSize = 18.sp , color = MaterialTheme.colors.onPrimary)
            }
        }
    }
}