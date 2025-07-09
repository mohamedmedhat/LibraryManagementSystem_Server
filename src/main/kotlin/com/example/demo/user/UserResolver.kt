package com.example.demo.user

import com.example.demo.user.dto.response.AuthResponseDto
import com.example.demo.user.dto.reuest.LoginRequestDto
import com.example.demo.user.dto.reuest.UserRequestDto
import jakarta.validation.Valid
import kotlinx.coroutines.runBlocking
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated

@Validated
@Controller
class UserResolver(
    private val userService: UserService
) {

    @MutationMapping
    fun register(@Valid @Argument data: UserRequestDto): User = runBlocking{
        return@runBlocking userService.register(data)
    }

    @MutationMapping
     fun login(@Valid @Argument data: LoginRequestDto): AuthResponseDto =  runBlocking {
        return@runBlocking userService.login(data.email, data.password)
    }
}
