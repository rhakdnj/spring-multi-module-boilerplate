package com.example.core.auth

import com.example.core.auth.properties.AuthProperties
import com.example.core.exception.UnauthorizedException
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class AuthTokenProvider(
    authProperties: AuthProperties,
) {
    private val key: SecretKey = Keys.hmacShaKeyFor(authProperties.tokenSecret.toByteArray())

    fun createAuthToken(
        userId: Long,
        expiry: Date,
        role: String? = null,
    ): AuthToken = AuthToken(userId, expiry, key, role)

    fun convertAuthToken(token: String): AuthToken = AuthToken(token, key)

    fun getAuthentication(authToken: AuthToken): Authentication {
        if (authToken.isValid) {
            val claims = authToken.tokenClaims
            val authorities =
                arrayOf(claims!![AUTHORITIES_KEY].toString())
                    .map(::SimpleGrantedAuthority)
                    .toList()
            return UsernamePasswordAuthenticationToken(User(claims.subject, "", authorities), authToken, authorities)
        }

        throw UnauthorizedException("올바르지 않은 토큰입니다.")
    }
}
