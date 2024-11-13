package kr.rhakdnj.admin.api.user.service

import kr.rhakdnj.admin.api.UseCase
import kr.rhakdnj.admin.api.user.controller.FindUserInfoResponse
import kr.rhakdnj.core.domain.user.repository.UserRepository
import kr.rhakdnj.core.enums.Role
import org.springframework.transaction.annotation.Transactional

@kr.rhakdnj.admin.api.UseCase
class FindUserInfoService(
	private val userRepository: UserRepository,
) {
	@Transactional(readOnly = true)
	fun findUserInfo(userId: Long): kr.rhakdnj.admin.api.user.controller.FindUserInfoResponse =
		kr.rhakdnj.admin.api.user.controller.FindUserInfoResponse(
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
