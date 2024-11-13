package kr.rhakndj.api.user.service

import kr.rhakdnj.core.domain.user.repository.UserRepository
import kr.rhakdnj.core.exception.NotFoundException
import kr.rhakndj.api.UseCase
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.annotation.Transactional

@UseCase
class ChangePasswordService(
	private val userRepository: UserRepository,
	private val passwordEncoder: PasswordEncoder,
) {
	@Transactional
	fun change(
		email: String,
		password: String,
	) {
		val user = userRepository.findByEmail(email) ?: throw NotFoundException("존재하지 않는 유저입니다.")
		user.encodePassword(passwordEncoder.encode(password))
	}
}
