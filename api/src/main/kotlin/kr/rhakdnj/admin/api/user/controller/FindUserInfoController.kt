package kr.rhakdnj.admin.api.user.controller

import kr.rhakdnj.admin.api.user.service.FindUserInfoService
import kr.rhakdnj.core.auth.LoginUser
import kr.rhakdnj.core.auth.TokenPayload
import kr.rhakdnj.core.auth.annotation.Authorized
import kr.rhakdnj.core.enums.Role
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Authorized
@RestController
@RequestMapping("/users")
class FindUserInfoController(
	private val service: kr.rhakdnj.admin.api.user.service.FindUserInfoService,
) {
	@GetMapping("/token-user-info")
	fun find(
		@LoginUser tokenPayload: TokenPayload,
	): kr.rhakdnj.admin.api.user.controller.FindUserInfoResponse = service.findUserInfo(tokenPayload.userId)
}

data class FindUserInfoResponse(
	val id: Long,
	val name: String,
	val nickname: String,
	val email: String,
	val status: Role,
	val profileImg: String,
	val highSchool: String? = null,
	val country: String? = null,
	val admissionCount: Int? = null,
)
