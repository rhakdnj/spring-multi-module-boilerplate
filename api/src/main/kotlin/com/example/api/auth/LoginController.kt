package com.example.api.auth

import com.example.api.auth.service.LoginService
import com.example.core.auth.properties.AuthProperties
import com.example.core.auth.properties.REFRESH_TOKEN
import com.example.core.util.addCookie
import com.example.core.util.deleteCookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController(
    private val service: LoginService,
    private val authProperties: AuthProperties,
) {
    @PostMapping("/login")
    fun login(
        @Valid @RequestBody request: LoginRequest,
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
    ): ResponseEntity<LoginResponse> {
        val (token, refreshToken) = service.login(request)

        deleteCookie(httpServletRequest, httpServletResponse, REFRESH_TOKEN)
        addCookie(httpServletResponse, REFRESH_TOKEN, refreshToken, authProperties.refreshTokenTimeoutMs / 1000)

        return ResponseEntity.ok(LoginResponse(token))
    }
}

data class LoginRequest(
    @field:Email
    val email: String,
    @field:NotBlank
    val password: String,
)

data class LoginResponse(
    val token: String,
)
