package com.example.demo.service

import com.example.demo.user.dto.RegisterInput
import com.example.demo.user.UserService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import java.util.*

@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var messageSource: MessageSource

    @Test
    fun `register should return localized error messages in Arabic`() {
        LocaleContextHolder.setLocale(Locale("ar"))

        val data = RegisterInput(
            name = "Test User",
            email = "ahmed@gmail.com",
            password = "password",
            roles = setOf("USER")
        )

        val exception = assertThrows<RuntimeException> {
            userService.register(data, LocaleContextHolder.getLocale())
        }

        val expectedMessage = messageSource.getMessage("error.emailAlreadyRegistered", null, Locale("ar"))
        assertEquals(expectedMessage, exception.message)
    }
}
