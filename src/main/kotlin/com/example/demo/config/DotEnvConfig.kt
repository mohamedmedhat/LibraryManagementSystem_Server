package com.example.demo.config

import io.github.cdimascio.dotenv.Dotenv
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DotEnvConfig {

    @Bean
    fun dotEnv(): Dotenv {
        return Dotenv.load()
    }
}