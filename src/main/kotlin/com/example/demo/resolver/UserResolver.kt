package com.example.demo.resolver

import com.example.demo.dto.user.AuthData
import com.example.demo.dto.user.LogInInput
import com.example.demo.dto.user.RegisterInput
import com.example.demo.model.User
import com.example.demo.service.UserService
import jakarta.validation.Valid
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class UserResolver(
    private val userService: UserService
) {

    @MutationMapping
    fun register(@Valid @Argument data: RegisterInput): User {
        try {
            return userService.register(data)
        } catch (e: Exception) {
            println("Failed to register user: ${e.message}")
            throw RuntimeException("Failed to register user: ${e.message}")
        }
    }

    @MutationMapping
    fun login(@Valid @Argument data: LogInInput): AuthData {
        try {
            return userService.login(data.email, data.password)
        } catch (e: Exception) {
            println("Login failed: ${e.message}")
            throw RuntimeException("Login failed: ${e.message}")
        }
    }
}
