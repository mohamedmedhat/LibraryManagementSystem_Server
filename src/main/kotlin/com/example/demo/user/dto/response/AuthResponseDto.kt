package com.example.demo.user.dto.response

import com.example.demo.user.User

data class AuthResponseDto(
    val token: String,
    val user: User
)
