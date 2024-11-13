package com.example.api.user.useCase

import com.example.core.annotation.UseCase
import com.example.core.domain.user.repository.UserRepository

@UseCase
class UserDuplicateCheckUseCase(
    private val userRepository: UserRepository,
) {
    fun checkExistedUser(
        email: String,
        username: String,
        birth: String,
    ): Boolean = userRepository.existsByEmailAndNameAndBirth(email, username, birth)
}
