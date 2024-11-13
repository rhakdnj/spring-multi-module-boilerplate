package kr.rhakdnj.core.domain.user.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import kr.rhakdnj.core.domain.user.QUser.user
import kr.rhakdnj.core.domain.user.User
import org.springframework.stereotype.Repository

interface FindUsersByEmailLikeOrNameLikeQuery {
	fun findUsersByEmailLikeOrNameLike(
		email: String?,
		username: String?,
	): List<User>
}

@Repository
class FindUsersByEmailLikeOrNameLikeQueryImpl(
	private val jpaQueryFactory: JPAQueryFactory,
) : FindUsersByEmailLikeOrNameLikeQuery {
	override fun findUsersByEmailLikeOrNameLike(
		email: String?,
		username: String?,
	): List<User> =
		jpaQueryFactory
			.selectFrom(user)
			.where(
				booleanBuilder(email, username),
			).orderBy(user.id.desc())
			.fetch()

	private fun booleanBuilder(
		email: String?,
		username: String?,
	): BooleanBuilder {
		val result = BooleanBuilder()

		email?.let {
			if (it.isNotBlank()) {
				result.and(user.email.like("%$it%"))
			}
		}

		username?.let {
			if (it.isNotBlank()) {
				result.and(user.name.like("%$it%"))
			}
		}

		return result
	}
}
