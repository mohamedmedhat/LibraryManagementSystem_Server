package com.example.demo.user.dto.reuest

import com.example.demo.shared.enums.Role
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserRequestDto(
    @field:NotBlank(message = "Name is mandatory")
    @field:Size(min = 6, max = 25, message = "Name should be between 6 and 25 characters")
    val name: String,

    @field:NotBlank(message = "Email is mandatory")
    @field:Email(message = "Email should be in valid format")
    val email: String,

    @field:NotBlank(message = "Password is mandatory")
    @field:Size(min = 6, max = 25, message = "Password should be between 6 and 25 characters")
    val password: String,

    val roles: Set<Role> = setOf(Role.USER)
)
