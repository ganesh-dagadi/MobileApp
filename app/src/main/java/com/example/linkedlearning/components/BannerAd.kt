package com.example.linkedlearning.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BannerAd(){
    Column(modifier = Modifier.height(250.dp).fillMaxWidth().background(Color.DarkGray)) {
        Text("Advertisement")
    }
}