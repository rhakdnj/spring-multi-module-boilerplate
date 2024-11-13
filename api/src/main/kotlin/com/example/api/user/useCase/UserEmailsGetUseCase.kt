package com.example.api.user.useCase

import com.example.core.annotation.UseCase
import com.example.core.domain.user.repository.UserRepository

@UseCase
class UserEmailsGetUseCase(
    private val userRepository: UserRepository,
) {
    fun findEmails(
        name: String,
        birth: String,
    ): List<String> =
        userRepository
            .findByNameAndBirth(name, birth)
            .map { mask(it.email) }

    private fun mask(email: String): String {
        val split = email.split("@")
        return split[0].substring(0, 3) + "****@" + split[1]
    }
}
