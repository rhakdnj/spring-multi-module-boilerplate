package com.example.api.user.controller

import com.example.api.user.useCase.UserDuplicateCheckUseCase
import com.example.core.exception.BadRequestException
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RestController

@RestController
class CheckExistedUserController(
    private val userService: UserDuplicateCheckUseCase,
) {
    // todo: for what purpose is this endpoint?
    @GetMapping("/users/validate-user")
    fun check(
        @Valid @ModelAttribute request: CheckExistedUserRequest,
    ): ResponseEntity<Unit> {
        val (email, name, birth) = request
        if (!userService.checkExistedUser(email, name, birth)) {
            throw BadRequestException("정보가 올바르지 않습니다.")
        }
        return ResponseEntity.ok().build()
    }
}

data class CheckExistedUserRequest(
    @field:Email(message = "이메일 형식이 아닙니다.")
    val email: String,
    @field:NotBlank(message = "이름은 필수입니다.")
    val name: String,
    @field:Size(min = 8, max = 8, message = "생년월일은 8자리여야 합니다.")
    val birth: String,
)
