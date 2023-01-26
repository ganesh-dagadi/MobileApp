package com.example.linkedlearning.data.authData

class UserSchema{
    var username: String = ""
    var user_id : String = ""
}

interface AuthRepoImpl{
    suspend fun setLoggedInUser(userId:String , username:String)
    suspend fun getLoggedInUser():UserSchema
}