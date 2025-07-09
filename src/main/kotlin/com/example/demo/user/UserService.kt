package com.example.demo.user

import com.example.demo.user.dto.response.AuthResponseDto
import com.example.demo.user.dto.reuest.UserRequestDto
import com.example.demo.user.exception.BadCredentialsException
import com.example.demo.user.exception.UserAlreadyExistException
import com.example.demo.utils.EncryptionUtil
import com.example.demo.utils.JwtUtil
import com.example.demo.utils.LocalizationUtil
import kotlinx.coroutines.CoroutineDispatcher
import org.springframework.stereotype.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Service
class UserService(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val customUserDetailsService: CustomUserDetailsService,
    private val encryptionUtil: EncryptionUtil,
    private val localizationUtil: LocalizationUtil,
    private val jwtUtil: JwtUtil
) {

    suspend fun register(data: UserRequestDto): User = withContext(dispatcher) {
        if (userRepository.existsByEmail(data.email)) {
            val message = localizationUtil.translateText("error.emailAlreadyRegistered", null)
            throw UserAlreadyExistException(message)
        }
        val encodedPassword = encryptionUtil.encodeString(data.password)
        val user = userMapper.toUserEntity(data, encodedPassword)
        return@withContext userRepository.save(user)
    }

    suspend fun login(email: String, password: String): AuthResponseDto = withContext(dispatcher) {
        val userDetails = customUserDetailsService.loadUserByUsername(email)
        if (encryptionUtil.comparePasswords(password, userDetails.password)) {
            val token = jwtUtil.generateToken(userDetails)
            val user = userRepository.findByEmail(email).get()
           return@withContext AuthResponseDto(user = user, token = token)
        } else {
            val message = localizationUtil.translateText("error.invalidCredentials", null)
            throw BadCredentialsException(message)
        }
    }
}
