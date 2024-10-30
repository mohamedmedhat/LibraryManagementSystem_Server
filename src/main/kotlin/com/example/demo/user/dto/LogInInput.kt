package com.example.demo.user.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LogInInput(
    @field:NotBlank(message = "email is mandatory")
    @field:Email(message = "email should be in valid format")
    val email: String,

    @field:NotBlank(message = "password is mandatory")
    @field:Size(min = 6, max = 25, message = "password should be between 6:25 char")
    val password: String
)
