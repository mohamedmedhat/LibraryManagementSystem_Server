package com.example.demo.book.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import jakarta.validation.constraints.NotNull

data class CreateBookInput(
    @field:NotBlank(message = "name is mandatory")
    @field:Size(min = 6, max = 25)
    val name: String,

    @field:NotBlank(message = "author is mandatory")
    val author: String,

    @field:NotNull(message = "publishYear is mandatory")
    val publishYear: Int,

    @field:NotNull(message = "price is mandatory")
    val price: Float
)

