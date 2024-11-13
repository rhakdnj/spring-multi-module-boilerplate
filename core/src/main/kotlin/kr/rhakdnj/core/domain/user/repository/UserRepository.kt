package kr.rhakdnj.core.domain.user.repository

import kr.rhakdnj.core.domain.user.User
import kr.rhakdnj.core.exception.NotFoundException
import org.springframework.data.jpa.repository.JpaRepository

fun UserRepository.findByIdOrNull(id: Long): User? = findById(id).orElse(null)

fun UserRepository.findByIdOrThrow(id: Long): User = findByIdOrNull(id) ?: throw NotFoundException("사용자가 존재하지 않습니다.")

interface UserRepository :
	JpaRepository<User, Long>,
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
