package kr.rhakdnj.admin.api.user.service

import kr.rhakdnj.admin.api.UseCase
import kr.rhakdnj.core.domain.user.repository.UserRepository

@kr.rhakdnj.admin.api.UseCase
class CheckExistedUserService(
	private val userRepository: UserRepository,
) {
	fun checkExistedUser(
		email: String,
		username: String,
		birth: String,
	): Boolean = userRepository.existsByEmailAndNameAndBirth(email, username, birth)
}
