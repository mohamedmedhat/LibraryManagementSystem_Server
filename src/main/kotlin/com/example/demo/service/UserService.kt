package com.example.demo.service

import com.example.demo.dto.user.AuthData
import com.example.demo.dto.user.RegisterInput
import com.example.demo.model.User
import com.example.demo.repository.UserRepository
import com.example.demo.utils.JwtUtil
import graphql.GraphQLException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val customUserDetailsService: CustomUserDetailsService,
    private val jwtUtil: JwtUtil
) {

    fun register(data: RegisterInput): User {
        if (userRepository.findByEmail(data.email).isPresent) {
            throw GraphQLException("Email is already registered")
        }
        val encodedPassword = passwordEncoder.encode(data.password)
        val user = User(
            name = data.name,
            email = data.email,
            password = encodedPassword,
            roles = data.roles
        )
        return userRepository.save(user)
    }

    fun login(email: String, password: String): AuthData {
        val userDetails = customUserDetailsService.loadUserByUsername(email)
        if (passwordEncoder.matches(password, userDetails.password)) {
            val token = jwtUtil.generateToken(userDetails)
            val user = userRepository.findByEmail(email).get()
            return AuthData(user = user, token = token)
        } else {
            throw GraphQLException("Invalid credentials.")
        }
    }
}
