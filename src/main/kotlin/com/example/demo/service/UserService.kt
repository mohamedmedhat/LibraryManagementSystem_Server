package com.example.demo.service

import com.example.demo.dto.user.AuthData
import com.example.demo.dto.user.RegisterInput
import com.example.demo.model.User
import com.example.demo.repository.UserRepository
import com.example.demo.utils.JwtUtil
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val customUserDetailsService: CustomUserDetailsService,
    private val messageSource: MessageSource,
    private val jwtUtil: JwtUtil
) {


    fun register(data: RegisterInput): User {
        if (userRepository.findByEmail(data.email).isPresent) {
            val locale = LocaleContextHolder.getLocale()
            val message = messageSource.getMessage("error.emailAlreadyRegistered", null, locale)
            throw RuntimeException(message)
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
            val locale = LocaleContextHolder.getLocale()
            val message = messageSource.getMessage("error.invalidCredentials", null, locale)
            throw RuntimeException(message)
        }
    }
}
