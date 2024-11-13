package kr.rhakdnj.core.util

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders

const val TOKEN_PREFIX = "Bearer "

fun HttpServletRequest.getAccessToken(): String? =
	this
		.getHeader(HttpHeaders.AUTHORIZATION)
		.takeIf {
			it?.startsWith(kr.rhakdnj.core.util.TOKEN_PREFIX, true) ?: false
		}?.substring(kr.rhakdnj.core.util.TOKEN_PREFIX.length)
