package com.example.linkedlearning.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.linkedlearning.data.api.course.data.Course
import com.example.linkedlearning.data.api.course.data.SecContent

@Composable
fun LectureCard(lectureData:SecContent , onClick:(link : String)->Unit){
    Row(modifier = Modifier.fillMaxWidth().clickable {
        onClick(lectureData.link)
    }) {
        Spacer(modifier = Modifier.width(20.dp))
        if(lectureData.resourceType == "video"){
            Icon(imageVector = Icons.Outlined.PlayArrow, contentDescription = null, tint = Color(237, 0 , 0))
        }else{
            Icon(imageVector = Icons.Outlined.Article, contentDescription = null, tint = Color(0, 0 , 0))
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(lectureData.title , style = TextStyle(fontSize = 18.sp));
    }
}