package com.example.linkedlearning.data.authData

import android.app.Application
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.linkedlearning.MainActivity


class AuthRepo(context:Context):AuthRepoImpl{
    val appContext = context.applicationContext
    private val sf = appContext.getSharedPreferences("auth" , MODE_PRIVATE)

    override suspend fun setUserId(id: String){
        with(sf.edit()){
            putString("userId" , id)
            apply()
        }
    }

    override suspend fun getUserId(): String? {
        return sf.getString("userId" , null)
    }

    override suspend fun getAccessToken():String? {
        return sf.getString("accessToken" , null)
    }

    override suspend fun setAccessToken(token: String) {
        with(sf.edit()){
            putString("accessToken" , token)
            apply()
        }
    }

    override suspend fun getRefreshToken():String? {
        return sf.getString("refreshToken" , null)
    }

    override suspend fun setRefreshToken(token: String) {
        with(sf.edit()){
            putString("refreshToken" , token)
            apply()
        }
    }

    override suspend fun getLoginState(): Boolean? {
        return sf.getBoolean("isLoggedIn" , false)
    }

    override suspend fun setLoginState(bool: Boolean) {
        with(sf.edit()){
            putBoolean("isLoggedIn" , bool)
            apply()
        }
    }

    override suspend fun clearSf() {
        val editor = sf.edit()
        editor.clear()
        editor.apply()
    }
}