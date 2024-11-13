package com.example.api.user.controller

import com.example.api.user.service.FindEmailsService
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class FindEmailsController(
	private val service: FindEmailsService,
) {
	@GetMapping("/users/id-list")
	fun find(
		@Valid @RequestBody request: FindUserEmailRequest,
	): ResponseEntity<FindUserEmailResponse> {
		val findEmails = service.findEmails(request.name, request.birth)
		return ResponseEntity.ok(
			FindUserEmailResponse(findEmails),
		)
	}
}

data class FindUserEmailRequest(
	@field:NotBlank(message = "사용자 이름은 필수값입니다.")
	val name: String,
	@field:NotBlank(message = "생년월일은 필수값입니다.")
	@field:Pattern(message = "생년월일은 yyyyMMdd 형식입니다.", regexp = "^[0-9]{8}$")
	val birth: String,
)

data class FindUserEmailResponse(
	val email: List<String>,
)
