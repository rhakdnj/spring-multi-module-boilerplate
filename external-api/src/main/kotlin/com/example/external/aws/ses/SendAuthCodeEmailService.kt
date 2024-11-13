package com.example.external.aws.ses

import com.example.external.aws.EmailService
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine

@Service
class SendAuthCodeEmailService(
    private val emailService: EmailService,
    private val templateEngine: SpringTemplateEngine,
) {
    suspend fun sendAuthCode(
        toAddress: String,
        authCode: String,
    ) {
        val context = Context()
        context.setVariable("authCode", authCode)
        val body = templateEngine.process("auth-code", context)

        emailService.sendMail(SUBJECT, body, listOf(toAddress))
    }

    companion object {
        private const val SUBJECT = "이메일 인증"
    }
}
