package com.example.api.auth

import com.example.core.auth.properties.AuthProperties
import com.example.core.auth.properties.REFRESH_TOKEN
import com.example.core.auth.service.AuthService
import com.example.core.exception.BadRequestException
import com.example.core.util.addCookie
import com.example.core.util.deleteCookie
import com.example.core.util.getAccessToken
import com.example.core.util.getCookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ReissueTokenController(
    private val authService: AuthService,
    private val authProperties: AuthProperties,
) {
    @GetMapping("/reissue-token")
    fun reissueToken(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
    ): ResponseEntity<ReissueTokenResponse> {
        val token = httpServletRequest.getAccessToken()
            ?: throw BadRequestException("expire token not found (in header)")
        val refreshToken = getCookie(httpServletRequest, REFRESH_TOKEN)?.value
            ?: throw BadRequestException("refresh token not found (in cookie)")

        val (newToken, newOrOldRefreshToken) = authService.reissueToken(token, refreshToken)

        if (newOrOldRefreshToken != refreshToken) {
            deleteCookie(httpServletRequest, httpServletResponse, REFRESH_TOKEN)
            addCookie(
                httpServletResponse,
                REFRESH_TOKEN,
                newOrOldRefreshToken,
                authProperties.refreshTokenTimeoutMs / 1_000
            )
        }

        return ResponseEntity.ok(ReissueTokenResponse(newToken))
    }
}

data class ReissueTokenResponse(
    val token: String,
)
