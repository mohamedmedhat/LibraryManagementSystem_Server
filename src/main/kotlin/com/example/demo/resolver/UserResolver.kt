package com.example.demo.resolver

import com.example.demo.dto.user.AuthData
import com.example.demo.dto.user.LogInInput
import com.example.demo.dto.user.RegisterInput
import com.example.demo.model.User
import com.example.demo.service.UserService
import jakarta.validation.Valid
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller

@Controller
class UserResolver(
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager
) {

    @QueryMapping
    fun currentUser(): User? {
        val authentication = SecurityContextHolder.getContext().authentication
        val username = authentication?.name ?: return null
        return userService.findUserByUsername(username)
    }

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
            val authToken = UsernamePasswordAuthenticationToken(data.email, data.password)
            val authentication = authenticationManager.authenticate(authToken)
            SecurityContextHolder.getContext().authentication = authentication

            val userDetails = authentication.principal as? UserDetails
                ?: throw RuntimeException("Failed to cast principal to UserDetails")

            val token = userService.login(userDetails.username, data.password)
            val user = userService.findUserByUsername(userDetails.username)

            return AuthData(token = token, user = user)
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException("Login failed: ${e.message}")
        }
    }
}
