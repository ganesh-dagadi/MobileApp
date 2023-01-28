package com.example.linkedlearning.data.api.auth.data

data class User(
    val __v: Int,
    val _id: String,
    val email: String,
    val isActive: Boolean,
    val isAdmin: Boolean,
    val isVerified: Boolean,
    val username: String
)