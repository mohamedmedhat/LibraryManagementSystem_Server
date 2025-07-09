package com.example.demo.user.dto.projection

import com.example.demo.shared.enums.Role

interface UserDetailsProjection {
    fun getEmail(): String
    fun getPassword(): String
    fun getRoles(): Set<Role>
}
