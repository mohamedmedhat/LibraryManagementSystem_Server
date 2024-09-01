package com.example.demo.service

import com.example.demo.dto.email.EmailDto
import com.example.demo.dto.user.AuthData
import com.example.demo.dto.user.RegisterInput
import com.example.demo.dto.user.ResetPasswordInput
import com.example.demo.model.User
import com.example.demo.repository.UserRepository
import com.example.demo.utils.JwtUtil
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val customUserDetailsService: CustomUserDetailsService,
    private val templateEngine: TemplateEngine,
    private val emailService: EmailService,
    private val messageSource: MessageSource,
    private val jwtUtil: JwtUtil
) {

    suspend fun register(data: RegisterInput): User = withContext(Dispatchers.IO) {
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
        userRepository.save(user)
    }

    suspend fun login(email: String, password: String): AuthData = withContext(Dispatchers.IO) {
        val userDetails = customUserDetailsService.loadUserByUsername(email)
        if (passwordEncoder.matches(password, userDetails.password)) {
            val token = jwtUtil.generateToken(userDetails)
            val user = userRepository.findByEmail(email).get()
            AuthData(user = user, token = token)
        } else {
            val locale = LocaleContextHolder.getLocale()
            val message = messageSource.getMessage("error.invalidCredentials", null, locale)
            throw RuntimeException(message)
        }
    }

    suspend fun resetPasswordByEmail(data: ResetPasswordInput): Boolean = withContext(Dispatchers.IO) {
        val user = userRepository.findByEmail(data.email).orElseThrow { RuntimeException("User not found") }
        val newPassword = generateRandomPassword()
        val securePassword = passwordEncoder.encode(newPassword)
        val context = Context().apply {
            setVariable("newPassword", newPassword)
        }
        val htmlContent = templateEngine.process("password-reset", context)
        val info = EmailDto(
            to = data.email,
            subject = "Password Reset Request",
            content = htmlContent
        )
        emailService.sendEmail(info)
        user.password = securePassword
        userRepository.save(user)
        return@withContext true
    }

    suspend fun generateRandomPassword(length: Int = 15): String = withContext(Dispatchers.IO) {
        val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&*+"
        return@withContext (1..length)
            .map { characters.random() }
            .joinToString("")
    }
}
