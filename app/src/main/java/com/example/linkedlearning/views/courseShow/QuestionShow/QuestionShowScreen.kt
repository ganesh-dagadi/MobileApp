package com.example.linkedlearning.views.courseShow.QuestionShow

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
import com.example.linkedlearning.components.BannerAd
import com.example.linkedlearning.components.LoadingScreen
import com.example.linkedlearning.components.Navbar
import com.example.linkedlearning.views.courseShow.NewQuestion.NewQuestionViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class QuestionShowViewModelFactory(private val context: Context) :
ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = QuestionShowViewModel(context) as T
}

@Composable
fun QuestionShowScreen(
    context:Context,
    onNavigate:(to:String)->Unit,
    showSnackBar: (msg:String)->Unit
){

    val viewModel:QuestionShowViewModel = viewModel(factory = QuestionShowViewModelFactory(context));
    val coroutineScope = rememberCoroutineScope()
//    runBlocking {viewModel.getQuestionData()}
    val question = viewModel.questionData.observeAsState(null).value
    val newAns = viewModel.newAnswerText.observeAsState(null)
    LaunchedEffect(key1 = true){
        viewModel.getQuestionData()
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
                        onNavigate(it) },  context = context)
                }
            }
        },
    ) {
        Column(modifier = Modifier
            .padding(20.dp)
            .verticalScroll(rememberScrollState())) {
            if(question ==null){
                LoadingScreen()
            }else{
                Text(question!!.title , style= TextStyle(fontSize = 30.sp) , modifier = Modifier.padding(bottom = 10.dp))
                if(question!!.descp != null){
                    Text(question!!.descp!!)
                }
                BannerAd(context)
                Text("Answers" , style= TextStyle(fontSize = 25.sp) , modifier = Modifier.padding(top = 10.dp))
                for(i in 0 until question.answers.size){
                    Text(question.answers[i].answer!! , fontSize = 17.sp , modifier = Modifier.padding(top = 10.dp , bottom = 10.dp))
                    Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.padding(end = 16.dp))
                }

                //New answer
                OutlinedTextField(value = newAns.value.toString(), onValueChange = {
                        newText->viewModel.setAnsText(newText)
                },
                    label = { Text(text = "New Answer") },
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp)
                        .height(300.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.secondary
                    )
                )
                Button(onClick = {
                    coroutineScope.launch {
                        if(viewModel.postAnswerQuestion()){
                            onNavigate(Routes.SHOWQUESTION)
                        }else{
                            showSnackBar("Something went wrong try again")
                        }
                    }

                } ,
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text("Submit" , modifier = Modifier.padding(top = 1.dp , bottom = 1.dp , start = 2.dp , end = 2.dp), fontSize = 18.sp , color = MaterialTheme.colors.onPrimary)
                }
                Spacer(modifier = Modifier.height(100.dp))
            }

        }
            }

}