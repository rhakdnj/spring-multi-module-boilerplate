package kr.rhakndj.api.user.service

import kr.rhakdnj.core.domain.user.repository.UserRepository
import kr.rhakndj.api.UseCase

@UseCase
class FindEmailsService(
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
