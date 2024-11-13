package kr.rhakdnj.admin.api.user.controller

import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import kr.rhakdnj.api.user.service.ChangePasswordService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "사용자 API")
@RestController
class ChangePasswordController(
	private val svc: ChangePasswordService,
) {
	@PutMapping("/users/password")
	fun changePassword(
		@Valid @RequestBody request: ChangePasswordRequest,
	): ResponseEntity<Unit> {
		svc.change(request.email, request.password)
		return ResponseEntity.ok().build()
	}
}

data class ChangePasswordRequest(
	@field:Email(message = "이메일이 유효하지 않습니다.")
	val email: String,
	@field:NotBlank(message = "비밀번호는 필수값입니다.")
	val password: String,
)
