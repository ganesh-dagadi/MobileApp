package com.example.linkedlearning.data.api.auth.data

data class LoginRes(
    val msg: String,
    val tokens: Tokens,
    val user: User,
    val err:String,
)