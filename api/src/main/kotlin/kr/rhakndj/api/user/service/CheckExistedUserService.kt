package kr.rhakndj.api.user.service

import kr.rhakdnj.core.domain.user.repository.UserRepository
import kr.rhakndj.api.UseCase

@UseCase
class CheckExistedUserService(
	private val userRepository: UserRepository,
) {
	fun checkExistedUser(
		email: String,
		username: String,
		birth: String,
	): Boolean = userRepository.existsByEmailAndNameAndBirth(email, username, birth)
}
