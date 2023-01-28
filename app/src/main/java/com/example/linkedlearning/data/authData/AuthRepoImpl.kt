package com.example.linkedlearning.data.authData


interface AuthRepoImpl{
    suspend fun setUserId(id :String)
    suspend fun getUserId():String?
}