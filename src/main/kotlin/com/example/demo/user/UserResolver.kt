package com.example.demo.user

import com.example.demo.user.dto.AuthData
import com.example.demo.user.dto.LogInInput
import com.example.demo.user.dto.RegisterInput
import com.example.demo.user.dto.ResetPasswordInput
import jakarta.validation.Valid
import kotlinx.coroutines.runBlocking
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class UserResolver(
    private val userService: UserService
) {

    @MutationMapping
    fun register(@Valid @Argument data: RegisterInput): User = runBlocking {
        try {
            return@runBlocking userService.register(data)
        } catch (e: Exception) {
            println("Failed to register user: ${e.message}")
            throw RuntimeException("Failed to register user: ${e.message}")
        }
    }

    @MutationMapping
    fun login(@Valid @Argument data: LogInInput): AuthData = runBlocking {
        try {
            return@runBlocking userService.login(data.email, data.password)
        } catch (e: Exception) {
            println("Login failed: ${e.message}")
            throw RuntimeException("Login failed: ${e.message}")
        }
    }

    @MutationMapping
    fun resetPassword(@Argument data: ResetPasswordInput): Boolean = runBlocking {
        try {
            return@runBlocking userService.resetPasswordByEmail(data)
        } catch (e: Exception) {
            throw RuntimeException("reset password failed ${e.message}")
        }
    }
}
