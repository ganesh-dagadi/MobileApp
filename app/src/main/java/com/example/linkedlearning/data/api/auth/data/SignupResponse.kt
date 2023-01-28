package com.example.linkedlearning.data.api.auth.data

data class SignupResponse(
    val msg: String,
    val err : String,
    val user: User
)