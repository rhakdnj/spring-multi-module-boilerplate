package com.example.api.user.service

import com.example.core.annotation.UseCase
import com.example.core.domain.user.repository.UserRepository

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
