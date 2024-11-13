package kr.rhakdnj.admin.api.user.service

import kr.rhakdnj.admin.api.UseCase
import kr.rhakdnj.core.domain.user.repository.UserRepository
import kr.rhakdnj.core.exception.ConflictException
import org.springframework.transaction.annotation.Transactional

@kr.rhakdnj.admin.api.UseCase
class ChangeUsernameAndNicknameService(
	private val userRepository: UserRepository,
) {
	@Transactional
	fun change(
		id: Long,
		username: String,
		nickname: String,
	) {
		val user = userRepository.findOneByIdOrThrow(id)
		user.changeName(username)
		if (userRepository.existsByNickname(nickname)) {
			throw ConflictException("이미 사용중인 닉네임입니다.")
		}
		user.changeNickname(nickname)
	}
}
