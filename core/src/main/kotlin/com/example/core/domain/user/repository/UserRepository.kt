package com.example.core.domain.user.repository

import com.example.core.domain.user.User
import com.example.core.exception.NotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

fun UserRepository.findByIdOrNull(id: UUID): User? = findById(id).orElse(null)

fun UserRepository.findByIdOrThrow(id: UUID): User = findByIdOrNull(id) ?: throw NotFoundException("사용자가 존재하지 않습니다.")

interface UserRepository :
    JpaRepository<User, UUID>,
    FindUsersByEmailLikeOrNameLikeQuery,
    FindOneQuery {
    fun findByEmailOrNickname(
        email: String,
        nickname: String,
    ): User?

    fun findByEmail(email: String): User?

    fun existsByEmail(email: String): Boolean

    fun existsByNickname(nickname: String): Boolean

    fun findByNameAndBirth(
        username: String,
        birth: String,
    ): List<User>

    fun existsByEmailAndNameAndBirth(
        email: String,
        name: String,
        birth: String,
    ): Boolean
}
