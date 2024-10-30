package com.example.demo.user.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class ResetPasswordInput(
    @field:NotBlank(message = "email is mandatory")
    @field:Email(message = "email should be valid format")
    val email: String
)
