package kr.rhakdnj.core.domain.user.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.rhakdnj.core.domain.user.QUser.user
import kr.rhakdnj.core.domain.user.User
import kr.rhakdnj.core.exception.NotFoundException
import org.springframework.stereotype.Repository

interface FindOneQuery {
	fun findOneByIdOrNull(id: Long): User?

	fun findOneByIdOrThrow(id: Long): User = findOneByIdOrNull(id) ?: throw NotFoundException("사용자가 존재하지 않습니다.")
}

@Repository
class FindOneQueryImpl(
	private val jpaQueryFactory: JPAQueryFactory,
) : FindOneQuery {
	override fun findOneByIdOrNull(id: Long): User? =
		jpaQueryFactory
			.selectFrom(user)
			.where(
				user.id.eq(id),
			).fetchOne()
}
