package com.example.linkedlearning.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
@Composable
fun dpToPx(dp: Dp): Int {
    val density = LocalDensity.current
    return density.run { dp.toPx() }.toInt()
}
@Preview
@Composable
fun CourseCard(
){
    Column (
        modifier = Modifier
            .padding(20.dp)
            .height(300.dp)
            .fillMaxWidth()
            .background(Color(240, 240, 250), shape = RoundedCornerShape(10.dp))
    ){

        val configuration = LocalConfiguration.current

        val screenWidth = configuration.screenWidthDp.dp
        var screenWidthPx = dpToPx(screenWidth)
        screenWidthPx -= dpToPx(dp = 50.dp)
        val heightImgPx= dpToPx(150.dp)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                Image(
                    // on below line we are adding the image url
                    // from which we will  be loading our image.
                    painter = rememberAsyncImagePainter("https://res.cloudinary.com/dxm68x3tm/image/upload/w_${screenWidthPx},h_${heightImgPx},c_scale/v1676117917/linkedLearning/ltomfva5vu19ma9xnsh2.png"),

                    // on below line we are adding content
                    // description for our image.
                    contentDescription = "gfg image",

                    // on below line we are adding modifier for our
                    // image as wrap content for height and width.

                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
            }


    }
}