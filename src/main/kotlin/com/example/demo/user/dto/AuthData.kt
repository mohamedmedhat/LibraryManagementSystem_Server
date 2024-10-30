package com.example.demo.user.dto

import com.example.demo.user.User

data class AuthData(
    val token: String,
    val user: User
)
