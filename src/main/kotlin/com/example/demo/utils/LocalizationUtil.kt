package com.example.demo.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Component

@Component
class LocalizationUtil (
   private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
   private val messageSource: MessageSource
){
    suspend fun translateText(error: String, args: Array<Any>?): String = withContext(dispatcher){
        val locale = LocaleContextHolder.getLocale()
        return@withContext messageSource.getMessage(error, args, locale)
    }
}