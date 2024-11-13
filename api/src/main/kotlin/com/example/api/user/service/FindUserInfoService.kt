package com.example.api.user.service

import com.example.core.annotation.UseCase
import com.example.api.user.controller.FindUserInfoResponse
import com.example.core.domain.user.repository.UserRepository
import com.example.core.enums.Role
import org.springframework.transaction.annotation.Transactional

@UseCase
class FindUserInfoService(
	private val userRepository: UserRepository,
) {
	@Transactional(readOnly = true)
	fun findUserInfo(userId: Long): FindUserInfoResponse =
		FindUserInfoResponse(
			id = 1,
			name = "name",
			nickname = "nickname",
			email = "email",
			status = Role.ROLE_USER,
			profileImg = "profileImg",
			highSchool = "highSchool",
			country = "country",
			admissionCount = 1,
		)
}
