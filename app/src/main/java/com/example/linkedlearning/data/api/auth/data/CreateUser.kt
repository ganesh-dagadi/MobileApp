package com.example.linkedlearning.data.api.auth.data

data class CreateUser(
    val email: String,
    val isAdmin: Boolean,
    val password: String,
    val username: String
)