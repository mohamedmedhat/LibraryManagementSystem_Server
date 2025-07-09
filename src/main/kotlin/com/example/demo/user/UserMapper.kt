package com.example.demo.user

import com.example.demo.user.dto.reuest.UserRequestDto
import org.springframework.stereotype.Component

@Component
class UserMapper {

    suspend fun toUserEntity(data: UserRequestDto, encryptedPass: String): User {
        return User(
            name = data.name,
            email = data.email,
            password = encryptedPass,
            roles = data.roles
        )
    }
}