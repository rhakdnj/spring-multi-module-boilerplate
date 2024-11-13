package com.example.api.auth.service

import com.example.api.auth.LoginRequest
import com.example.core.annotation.UseCase
import com.example.core.auth.service.AuthService
import com.example.core.domain.user.repository.UserRepository
import com.example.core.exception.UnauthorizedException
import org.springframework.security.crypto.password.PasswordEncoder

@UseCase
class LoginService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authService: AuthService,
) {
    fun login(request: LoginRequest): Pair<String, String> {
        val (email, rawPassword) = request
        val findUser = userRepository.findByEmail(email)
        if (findUser == null || !passwordEncoder.matches(rawPassword, findUser.password)) {
            throw UnauthorizedException("이메일 또는 비밀번호를 잘못 입력했습니다.")
        }
        return authService.createTokens(findUser)
    }
}
