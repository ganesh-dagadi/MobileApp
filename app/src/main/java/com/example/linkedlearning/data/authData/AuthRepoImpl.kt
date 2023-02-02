package com.example.linkedlearning.data.authData


interface AuthRepoImpl{
    suspend fun setUserId(id :String)
    suspend fun getUserId():String?
    suspend fun setAccessToken(token:String)
    suspend fun getAccessToken():String?
    suspend fun setRefreshToken(token:String)
    suspend fun getRefreshToken():String?
    suspend fun setLoginState(bool:Boolean)
    suspend fun getLoginState():Boolean?
    suspend fun clearSf():Unit
}