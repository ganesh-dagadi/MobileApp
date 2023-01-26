package com.example.linkedlearning.data.authData

import android.app.Application


class AuthRepo:Application(){
    private val context = applicationContext
    private val sf = context.getSharedPreferences("auth" , MODE_PRIVATE)
}