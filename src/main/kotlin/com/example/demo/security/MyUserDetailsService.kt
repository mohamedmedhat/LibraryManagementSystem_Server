package com.example.demo.security

import com.example.demo.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class MyUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByName(username)
            .orElseThrow { UsernameNotFoundException("User not found with username: $username") }

        return org.springframework.security.core.userdetails.User(
            user.name,
            user.password,
            user.roles.map { SimpleGrantedAuthority(it) }
        )
    }
}
