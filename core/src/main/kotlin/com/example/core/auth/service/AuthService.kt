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
		val claims = convertedToken.expiredTokenClaims ?: throw UnauthorizedException("토큰이 만료되지 않았거나 올바르지 않습니다.")

		val userId = claims.subject.toLong()
		val role = Role.valueOf(claims[AUTHORITIES_KEY] as String)

		val convertedRefreshToken = authTokenProvider.convertAuthToken(refreshToken)
		if (!convertedRefreshToken.isValid) {
			throw UnauthorizedException("리프레쉬 토큰이 올바르지 않습니다.")
		}

		val findRefreshToken = refreshTokenRepository.findByUserId(userId)
		if (findRefreshToken == null || findRefreshToken != refreshToken) {
			throw UnauthorizedException("리프레쉬 토큰이 올바르지 않습니다.")
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
		userId: Long,
		role: Role,
		now: Date,
	): String =
		authTokenProvider
			.createAuthToken(
				userId = userId,
				Date(now.time + authProperties.tokenTimeoutMs),
				role.name,
			).token

	private fun createRefreshToken(
		userId: Long,
		now: Date,
	): String =
		authTokenProvider
			.createAuthToken(
				userId = userId,
				Date(now.time + authProperties.refreshTokenTimeoutMs),
			).token
}
