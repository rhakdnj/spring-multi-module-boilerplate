package com.example.api.user.controller

import com.example.api.user.domainservice.UserService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "사용자 API")
@RestController
class CheckDuplicatedController(
	private val userService: UserService,
) {
	@PostMapping("/users/duplicate-email")
	fun check(
		@Valid @RequestBody request: CheckDuplicatedEmailRequest,
	): ResponseEntity<Unit> {
		userService.checkDuplicatedEmail(request.email)
		return ResponseEntity.ok().build()
	}

	@PostMapping("/users/duplicate-nickname")
	fun check(
		@Valid @RequestBody request: CheckDuplicatedNicknameRequest,
	): ResponseEntity<Unit> {
		userService.checkDuplicatedNickname(request.nickname)
		return ResponseEntity.ok().build()
	}
}

data class CheckDuplicatedEmailRequest(
	val email: String,
)

data class CheckDuplicatedNicknameRequest(
	val nickname: String,
)
