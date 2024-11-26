package com.example.api.user.controller

import com.example.api.user.useCase.UserPasswordChangeUseCase
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ChangePasswordController(
    private val svc: UserPasswordChangeUseCase,
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
