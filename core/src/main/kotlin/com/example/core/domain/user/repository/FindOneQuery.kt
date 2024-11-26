package com.example.core.domain.user.repository

import com.example.core.domain.user.QUser.user
import com.example.core.domain.user.User
import com.example.core.exception.NotFoundException
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.util.UUID

interface FindOneQuery {
    fun findOneByIdOrNull(id: UUID): User?

    fun findOneByIdOrThrow(id: UUID): User = findOneByIdOrNull(id) ?: throw NotFoundException("사용자가 존재하지 않습니다.")
}

@Repository
class FindOneQueryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : FindOneQuery {
    override fun findOneByIdOrNull(id: UUID): User? =
        jpaQueryFactory
            .selectFrom(user)
            .where(
                user.id.eq(id),
            ).fetchOne()
}
