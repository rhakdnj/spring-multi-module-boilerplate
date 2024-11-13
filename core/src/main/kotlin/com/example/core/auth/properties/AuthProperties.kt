package com.example.core.auth.properties

import org.springframework.boot.context.properties.ConfigurationProperties

const val REFRESH_TOKEN = "refreshToken"

@ConfigurationProperties(prefix = "app.auth")
data class AuthProperties(
	val tokenSecret: String,
	val tokenTimeoutMs: Long,
	val refreshTokenTimeoutMs: Long,
)
