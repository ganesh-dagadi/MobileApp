package com.example.linkedlearning.components

import android.os.CountDownTimer
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun TimerComponent(
    seconds:Long,
    resetResend:()->Unit
){
    val millisInFuture: Long = seconds * 1000

    val timeData = remember {
        mutableStateOf(millisInFuture)
    }

    val countDownTimer =
        object : CountDownTimer(millisInFuture, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeData.value = millisUntilFinished
            }

            override fun onFinish() {
                resetResend()
            }
        }

    DisposableEffect(key1 = "key") {
        countDownTimer.start()
        onDispose {
            countDownTimer.cancel()
        }
    }
    Column() {
        Text(
            text = "Resend in:"
        )
        Text(
            text = (timeData.value/1000).toString()
        )
    }
}