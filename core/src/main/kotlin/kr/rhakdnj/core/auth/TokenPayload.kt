package kr.rhakdnj.core.auth

import kr.rhakdnj.core.enums.Role

data class TokenPayload(
	val userId: Long,
	val role: Role,
)
