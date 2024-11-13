package com.example.api.user.controller

import com.example.api.user.service.FindUserInfoService
import com.example.core.auth.LoginUser
import com.example.core.auth.TokenPayload
import com.example.core.auth.annotation.Authorized
import com.example.core.enums.Role
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
