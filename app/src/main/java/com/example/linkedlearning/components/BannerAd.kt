package com.example.linkedlearning.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun BannerAd(context: Context){
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            AdView(context).apply {
                // on below line specifying ad unit id
                setAdSize(AdSize(350 ,300))
                // currently added a test ad unit id.
                adUnitId = "ca-app-pub-3940256099942544/6300978111"
                // calling load ad to load our ad.
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}

@Composable
fun smallBannerAd(){
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            AdView(context).apply {
                // on below line specifying ad unit id
                setAdSize(AdSize.BANNER)
                // currently added a test ad unit id.
                adUnitId = "ca-app-pub-3940256099942544/6300978111"
                // calling load ad to load our ad.
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}