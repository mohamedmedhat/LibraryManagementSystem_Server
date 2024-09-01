package com.example.demo.service

import com.example.demo.dto.email.EmailDto
import jakarta.mail.MessagingException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailService(private val emailSender: JavaMailSender) {

    @Throws(MessagingException::class)
    suspend fun sendEmail(data: EmailDto) = withContext(Dispatchers.IO) {
        try {
            val mimeMessage = emailSender.createMimeMessage()
            val helper = MimeMessageHelper(mimeMessage, true)
            helper.setFrom("mmr973320@gmail.com")
            helper.setTo(data.to)
            helper.setSubject(data.subject)
            helper.setText(data.content, true)
            emailSender.send(mimeMessage)
        } catch (e: MessagingException) {
            throw RuntimeException("Failed to send email: ${e.message}", e)
        }
    }

}