package com.example.core.auth.service

import com.example.core.auth.AUTHORITIES_KEY
import com.example.core.auth.AuthTokenProvider
import com.example.core.auth.properties.AuthProperties
import com.example.core.domain.user.User
import com.example.core.enums.Role
import com.example.core.exception.UnauthorizedException
import com.example.core.redis.repository.RefreshTokenRepository
import org.springframework.stereotype.Service
import java.util.Date
import java.util.UUID
import kotlin.time.Duration.Companion.days

@Service
class AuthService(
    private val authProperties: AuthProperties,
    private val authTokenProvider: AuthTokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
) {
    fun createTokens(findUser: User): Pair<String, String> {
        val role = findUser.role
        val now = Date()

        val token = createToken(findUser.id, role, now)
        val refreshToken = createRefreshToken(findUser.id, now)

        refreshTokenRepository.save(findUser.id, refreshToken, authProperties.refreshTokenTimeoutMs)

        return token to refreshToken
    }

    fun reissueToken(
        token: String,
        refreshToken: String,
    ): Pair<String, String> {
        val convertedToken = authTokenProvider.convertAuthToken(token)
        val claims = convertedToken.expiredTokenClaims ?: throw UnauthorizedException("token is not expired or invalid")

        val userId = UUID.fromString(claims.subject.toString())
        val role = Role.valueOf(claims[AUTHORITIES_KEY] as String)

        val convertedRefreshToken = authTokenProvider.convertAuthToken(refreshToken)
        if (!convertedRefreshToken.isValid) {
            throw UnauthorizedException("refresh token is invalid")
        }

        val findRefreshToken = refreshTokenRepository.findByUserId(userId)
        if (findRefreshToken == null || findRefreshToken != refreshToken) {
            throw UnauthorizedException("refresh token is invalid")
        }

        val now = Date()
        val newToken = createToken(userId, role, now)

        val validTime = convertedRefreshToken.tokenClaims!!.expiration.time - now.time
        val refreshTime = 3.days.inWholeMilliseconds
        if (validTime <= refreshTime) {
            val newRefreshToken = createRefreshToken(userId, now)
            return newToken to newRefreshToken
        }

        return newToken to findRefreshToken
    }

    private fun createToken(
        userId: UUID,
        role: Role,
        now: Date,
    ): String =
        authTokenProvider.createAuthToken(
            userId = userId,
            Date(now.time + authProperties.tokenTimeoutMs),
            role.name,
        ).token

    private fun createRefreshToken(
        userId: UUID,
        now: Date,
    ): String =
        authTokenProvider.createAuthToken(
            userId = userId,
            Date(now.time + authProperties.refreshTokenTimeoutMs),
        ).token
}
