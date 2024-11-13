package com.example.api.user.domainservice

import com.example.core.domain.user.repository.UserRepository
import com.example.core.exception.ConflictException
import org.springframework.stereotype.Service

@Service
class UserDomainService(
    private val userRepository: UserRepository,
) {
    fun checkDuplicatedEmailOrNickname(
        email: String,
        nickname: String,
    ) {
        userRepository.findByEmailOrNickname(email, nickname)?.let {
            if (it.email == email) {
                throw ConflictException("${email}은 현재 사용중입니다.")
            }
            throw ConflictException("${nickname}은 현재 사용중입니다.")
        }
    }

    fun checkDuplicatedEmail(email: String) {
        if (userRepository.existsByEmail(email)) {
            throw ConflictException("${email}은 현재 사용중입니다.")
        }
    }

    fun checkDuplicatedNickname(nickname: String) {
        if (userRepository.existsByNickname(nickname)) {
            throw ConflictException("${nickname}은 현재 사용중입니다.")
        }
    }
}
