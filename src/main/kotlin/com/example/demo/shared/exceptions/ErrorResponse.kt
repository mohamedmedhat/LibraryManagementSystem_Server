package com.example.demo.shared.exceptions

data class ErrorResponse(
    val status: Int,
    val message: String,
    val path: String
)
