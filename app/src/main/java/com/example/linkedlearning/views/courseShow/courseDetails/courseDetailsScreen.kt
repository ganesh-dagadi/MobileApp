package com.example.linkedlearning.views.courseShow.courseDetails

import android.content.Context
import android.util.Log
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.linkedlearning.views.dashboard.DashBoardViewModel
import com.example.linkedlearning.views.dashboard.DashboardScreenViewModelFactory
import kotlinx.coroutines.runBlocking

class CourseDetailsViewModelFactory(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = CourseDetailsViewModel(context) as T

}
@Composable
fun CourseDetailsScreen(
    onNavigate :(to:String)->Unit,
    context: Context
){
    val viewModel:CourseDetailsViewModel = viewModel(factory = CourseDetailsViewModelFactory(context))
    runBlocking {
        val courseData = viewModel.getCourseData()
    }
    val courseData = viewModel.courseData
    Log.i("APIEvent" , courseData.toString())

    Text("Yuval salomon und Sabrina bussandri liebt Ganesh Dagadi")
}