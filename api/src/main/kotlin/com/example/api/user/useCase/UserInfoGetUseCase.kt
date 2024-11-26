package com.example.api.user.useCase

import com.example.api.user.controller.FindUserInfoResponse
import com.example.core.annotation.UseCase
import com.example.core.domain.user.repository.UserRepository
import com.example.core.enums.Role
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@UseCase
class UserInfoGetUseCase(
    private val userRepository: UserRepository,
) {
    @Transactional(readOnly = true)
    fun findUserInfo(userId: UUID): FindUserInfoResponse =
        FindUserInfoResponse(
            id = 1,
            name = "name",
            nickname = "nickname",
            email = "email",
            status = Role.ROLE_USER,
            profileImg = "profileImg",
        )
}
