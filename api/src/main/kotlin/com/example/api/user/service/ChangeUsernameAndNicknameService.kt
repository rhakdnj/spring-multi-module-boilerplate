package com.example.api.user.service

import com.example.core.annotation.UseCase
import com.example.core.domain.user.repository.UserRepository
import com.example.core.exception.ConflictException
import org.springframework.transaction.annotation.Transactional

@UseCase
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
