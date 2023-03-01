package com.example.linkedlearning.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.linkedlearning.data.api.course.data.Course

@Composable
fun dpToPx(dp: Dp): Int {
    val density = LocalDensity.current
    return density.run { dp.toPx() }.toInt()
}
@Composable
fun CourseCard(
    courseDetail:Course,
    onCardClick:()->Unit
){
    Column (
        modifier = Modifier
            .padding(start=40.dp , end = 40.dp)
            .height(300.dp)
            .fillMaxWidth()
            .clickable { onCardClick() }
            .background(Color(240, 240, 250), shape = RoundedCornerShape(10.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        val configuration = LocalConfiguration.current

        val screenWidth = configuration.screenWidthDp.dp
        var screenWidthPx = dpToPx(screenWidth)
        screenWidthPx -= dpToPx(dp = 80.dp)
        val heightImgPx= dpToPx(150.dp)
        val newImageString = courseDetail.image.substring(0, 49)+ "/w_$screenWidthPx,h_$heightImgPx,c_scale" + courseDetail.image.substring(49)
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
                    contentDescription = "gfg image",

                    // on below line we are adding modifier for our
                    // image as wrap content for height and width.

                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )

            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(courseDetail.title, style= TextStyle(fontSize = 20.sp) , modifier = Modifier.padding(start=5.dp))
            Spacer(modifier = Modifier.height(10.dp))
            Row() {
                repeat(4){
                    Icon(imageVector = Icons.Outlined.Star, contentDescription = null, tint = Color(237, 226, 24))
                }
                repeat(1){
                    Icon(imageVector = Icons.Outlined.Star, contentDescription = null, tint = Color(186, 186, 184))
                }
            }
        Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth().height(40.dp) , verticalAlignment = Alignment.CenterVertically){
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    painter = rememberImagePainter(
                        data = "https://res.cloudinary.com/dxm68x3tm/image/upload/w_40,h_40,c_scale/v1676117917/linkedLearning/ltomfva5vu19ma9xnsh2.png",
                        builder = {
                            // You can customize the image loading with options here
                            // For example, you can set a placeholder or error drawable
                        }
                    ),
                    contentDescription = "Profile picture",
                    modifier = Modifier.size(40.dp).clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text("Course creator")
                Spacer(modifier = Modifier.width(30.dp))
                Text("3000 enrolled")
            }
    }
}