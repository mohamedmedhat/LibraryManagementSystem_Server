package com.example.demo.config

import io.github.cdimascio.dotenv.Dotenv
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration

@Configuration
class DotEnvConfig {

    @PostConstruct
    fun init() {
        Dotenv.load()
    }
}