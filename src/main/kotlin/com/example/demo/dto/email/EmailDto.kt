package com.example.demo.dto.email

import jakarta.validation.constraints.NotBlank

data class EmailDto(

    @field:NotBlank(message = "to is mandatory")
    val to: String,

    @field:NotBlank(message = "subject is mandatory")
    val subject: String,

    @field:NotBlank(message = "content is mandatory")
    val content: String,
)
