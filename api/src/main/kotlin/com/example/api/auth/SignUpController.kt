package com.example.api.auth

import com.example.api.auth.service.SignUpService
import com.example.core.domain.user.User
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class SignUpController(
    private val service: SignUpService,
) {
    @RequestMapping("/users")
    fun signUp(
        @Valid @RequestBody request: SignUpRequest,
    ): ResponseEntity<UUID> = ResponseEntity.ok(service.signUp(request))
}

data class SignUpRequest(
    @field:Email
    val email: String,
    @field:NotBlank
    val password: String,
    @field:NotBlank
    val name: String,
    @field:NotBlank
    val nickname: String,
    @field:Pattern(regexp = "^[0-9]{8}$")
    val birth: String,
) {
    fun toEntity() =
        User(
            email = email,
            password = password,
            name = name,
            nickname = nickname,
            birth = birth,
        )
}
