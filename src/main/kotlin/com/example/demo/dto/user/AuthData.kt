package com.example.demo.dto.user

import com.example.demo.model.User
import java.util.*

data class AuthData(
    val token: String,
    val user: User
)
