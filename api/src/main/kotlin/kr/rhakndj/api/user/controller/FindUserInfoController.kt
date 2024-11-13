package kr.rhakndj.api.user.controller

import kr.rhakdnj.core.auth.LoginUser
import kr.rhakdnj.core.auth.TokenPayload
import kr.rhakdnj.core.auth.annotation.Authorized
import kr.rhakdnj.core.enums.Role
import kr.rhakndj.api.user.service.FindUserInfoService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Authorized
@RestController
@RequestMapping("/users")
class FindUserInfoController(
	private val service: FindUserInfoService,
) {
	@GetMapping("/token-user-info")
	fun find(
		@LoginUser tokenPayload: TokenPayload,
	): FindUserInfoResponse = service.findUserInfo(tokenPayload.userId)
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
