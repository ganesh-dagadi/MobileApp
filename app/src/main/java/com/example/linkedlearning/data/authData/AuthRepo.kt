package com.example.linkedlearning.data.authData

import android.app.Application
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.linkedlearning.MainActivity


class AuthRepo(context: Context):AuthRepoImpl{

    private val sf = context.getSharedPreferences("auth" , MODE_PRIVATE)

    override suspend fun setUserId(id: String){
        with(sf.edit()){
            putString("userId" , id)
            apply()
        }
    }

    override suspend fun getUserId(): String? {
        return sf.getString("userId" , null)
        return "hi"
    }
}