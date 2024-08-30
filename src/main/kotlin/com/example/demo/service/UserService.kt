package com.example.demo.service

import com.example.demo.dto.user.RegisterInput
import com.example.demo.model.User
import com.example.demo.repository.UserRepository
import com.example.demo.utils.JwtUtil
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil
) : UserDetailsService {

    fun register(data: RegisterInput): User {
        val encodedPassword = passwordEncoder.encode(data.password)
        val user = User(
            name = data.name,
            email = data.email,
            password = encodedPassword,
            roles = data.roles
        )
        val savedUser = userRepository.save(user)
        println("Registered user: $savedUser")
        return savedUser
    }


    fun login(email: String, password: String): String {
        val user = userRepository.findByEmail(email).orElseThrow {
            IllegalArgumentException("User not found")
        }
        if (!passwordEncoder.matches(password, user.password)) {
            throw IllegalArgumentException("Invalid credentials")
        }
        return jwtUtil.generateToken(user)
    }

    fun findUserByUsername(username: String): User? {
        return userRepository.findByName(username).orElse(null)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username).orElseThrow {
            IllegalArgumentException("User not found")
        }
        return org.springframework.security.core.userdetails.User(
            user.email,
            user.password,
            user.roles.map { SimpleGrantedAuthority(it) }
        )
    }
}
