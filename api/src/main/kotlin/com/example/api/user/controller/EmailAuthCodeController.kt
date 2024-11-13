package com.example.api.user.controller

import com.example.core.exception.BadRequestException
import com.example.core.redis.repository.RedisKeyValueRepository
import com.example.external.aws.ses.SendAuthCodeEmailService
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size
import kotlinx.coroutines.runBlocking
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class EmailAuthCodeController(
	private val emailService: SendAuthCodeEmailService,
	private val redisRepository: RedisKeyValueRepository,
) {
	@PostMapping("/users/send-email")
	fun sendAuthCode(
		@Valid @RequestBody request: SendAuthCodeRequest,
	): ResponseEntity<Unit> {
		val authCode = generateAuthenticationCode()

		runBlocking {
			emailService.sendAuthCode(
				toAddress = request.email,
				authCode = authCode,
			)
		}

		redisRepository.save(request.email, authCode, 300) // 5분

		return ResponseEntity.ok().build()
	}

	@PostMapping("/users/check-auth-code")
	fun checkAuthCode(
		@Valid @RequestBody request: CheckAuthCodeRequest,
	): ResponseEntity<Unit> {
		val savedAuthCode = redisRepository.find(request.email)

		if (savedAuthCode != request.authCode) {
			throw BadRequestException("인증번호가 올바르지 않습니다.")
		}

		return ResponseEntity.ok().build()
	}

	private fun generateAuthenticationCode(): String = (1000..9999).random().toString()
}

data class SendAuthCodeRequest(
	@field:Email(message = "이메일 형식이 아닙니다.")
	val email: String,
)

data class CheckAuthCodeRequest(
	@field:Email(message = "이메일 형식이 아닙니다.")
	val email: String,
	@field:Size(min = 4, max = 4, message = "인증번호는 4자리입니다.")
	val authCode: String,
)
