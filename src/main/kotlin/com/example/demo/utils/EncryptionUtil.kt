package com.example.demo.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class EncryptionUtil(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val passwordEncoder: PasswordEncoder
) {

    suspend fun encodeString(item: String): String = withContext(dispatcher) {
        return@withContext passwordEncoder.encode(item)
    }

    suspend fun comparePasswords(rawPass: String, pass: String): Boolean = withContext(dispatcher) {
        return@withContext passwordEncoder.matches(rawPass, pass)
    }
}