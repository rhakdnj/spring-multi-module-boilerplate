package kr.rhakdnj.admin.api.user.util

private val EMAIL_REGEX = Regex("^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")

fun checkEmailFormat(email: String): Boolean = EMAIL_REGEX.matches(email)
