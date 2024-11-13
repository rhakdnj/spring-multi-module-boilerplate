package com.example.api.auth.service

import com.example.api.auth.SignUpRequest
import com.example.api.user.domainservice.UserDomainService
import com.example.core.domain.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class SignUpService(
    private val userService: UserDomainService,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    fun signUp(request: SignUpRequest): Long {
        userService.checkDuplicatedEmailOrNickname(request.email, request.nickname)

        val user = request.toEntity()
        user.encodePassword(passwordEncoder.encode(user.password))

        return userRepository.save(user).id
    }
}
