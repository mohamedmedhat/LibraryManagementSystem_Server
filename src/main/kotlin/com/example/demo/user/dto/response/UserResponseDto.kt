package com.example.demo.user.dto.response

import com.example.demo.shared.enums.Role

data class UserResponseDto(
    val id: Long,
    val name: String,
    val email : String,
    val roles: Set<Role>
)
