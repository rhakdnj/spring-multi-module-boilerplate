package com.example.api.user.service

import com.example.core.annotation.UseCase
import com.example.core.domain.user.repository.UserRepository
import com.example.core.exception.NotFoundException
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
