package kr.rhakndj.api.user.service

import kr.rhakdnj.core.domain.user.repository.UserRepository
import kr.rhakdnj.core.enums.Role
import kr.rhakndj.api.UseCase
import kr.rhakndj.api.user.controller.FindUserInfoResponse
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
