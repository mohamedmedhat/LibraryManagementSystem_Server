package com.example.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.CookieLocaleResolver
import java.util.*

@Configuration
class LocaleConfig {

    @Bean
    fun localeResolver(): LocaleResolver {
        val localeResolver = CookieLocaleResolver()
        localeResolver.setDefaultLocale(Locale.ENGLISH)
        localeResolver.setCookieName("localeCookie")
        localeResolver.setCookieMaxAge(3600) // Set cookie expiration
        return localeResolver
    }

    @Bean
    fun messageSource(): ReloadableResourceBundleMessageSource {
        val messageResource = ReloadableResourceBundleMessageSource()
        messageResource.setBasename("classpath:messages")
        messageResource.setDefaultEncoding("UTF-8")
        return messageResource
    }
}
