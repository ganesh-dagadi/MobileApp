package com.example.linkedlearning.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.linkedlearning.SECRETS.BannerId
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun BannerAd(context: Context){
    Column(modifier = Modifier.padding(10.dp).fillMaxWidth().border(1.dp , Color.DarkGray, RoundedCornerShape(30), ).padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("It takes a lot of effort and money to keep the application ad-free." , style = TextStyle(fontSize = 20.sp , textAlign = TextAlign.Center))
        Text("We rely on your support.")
        Text("Donate by logging into www.linkedlearning.in")
    }
}

@Composable
fun smallBannerAd(){
    Text("Hello")
}