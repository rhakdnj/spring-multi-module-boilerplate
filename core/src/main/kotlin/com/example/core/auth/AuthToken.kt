package com.example.core.auth

import com.example.core.logging.Logger.Companion.log
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import java.util.Date
import javax.crypto.SecretKey

const val AUTHORITIES_KEY: String = "role"

class AuthToken(
	var token: String,
	private val key: SecretKey,
) {
	constructor(userId: Long, expiry: Date, key: SecretKey, role: String? = null) : this("", key) {
		token =
			if (role != null) {
				createAuthToken(userId, expiry, role)
			} else {
				createAuthToken(userId, expiry)
			}
	}

	val tokenClaims: Claims?
		get() {
			try {
				return parseJwt()
			} catch (e: SecurityException) {
				log.warn { "JWT 서명이 잘못되었습니다." }
			} catch (e: MalformedJwtException) {
				log.warn { "JWT 토큰이 잘못되었습니다." }
			} catch (e: ExpiredJwtException) {
				log.warn { "JWT 토큰이 만료되었습니다." }
			} catch (e: UnsupportedJwtException) {
				log.warn { "지원되지 않는 JWT 토큰입니다." }
			} catch (e: IllegalArgumentException) {
				log.warn { "핸들러의 JWT 토큰 컴팩트가 잘못되었습니다." }
			} catch (e: SignatureException) {
				log.warn { "JWT 서명이 일치하지 않습니다." }
			}
			return null
		}

	val expiredTokenClaims: Claims?
		get() {
			try {
				parseJwt()
			} catch (e: ExpiredJwtException) {
				log.info { "JWT 토큰이 만료되었습니다." }
				return e.claims
			} catch (e: SecurityException) {
				log.warn { "JWT 서명이 잘못되었습니다." }
			} catch (e: MalformedJwtException) {
				log.warn { "JWT 토큰이 잘못되었습니다." }
			} catch (e: UnsupportedJwtException) {
				log.warn { "지원되지 않는 JWT 토큰입니다." }
			} catch (e: IllegalArgumentException) {
				log.warn { "핸들러의 JWT 토큰 컴팩트가 잘못되었습니다." }
			} catch (e: SignatureException) {
				log.warn { "JWT 서명이 일치하지 않습니다." }
			}
			return null
		}

	val isValid: Boolean
		get() = tokenClaims != null

	private fun parseJwt(): Claims? =
		Jwts
			.parser()
			.verifyWith(key)
			.build()
			.parseSignedClaims(token)
			.payload

	private fun createAuthToken(
		userId: Long,
		expiry: Date,
	): String =
		Jwts
			.builder()
			.subject(userId.toString())
			.signWith(key)
			.expiration(expiry)
			.compact()

	private fun createAuthToken(
		userId: Long,
		expiry: Date,
		role: String,
	): String =
		Jwts
			.builder()
			.subject(userId.toString())
			.claim(AUTHORITIES_KEY, role)
			.signWith(key)
			.expiration(expiry)
			.compact()
}
