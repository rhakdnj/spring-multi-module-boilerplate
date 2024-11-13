package com.example.core.dto.response

data class ErrorResponse(
	val message: String,
)

data class BadRequestResponse(
	val message: String,
	val validations: MutableMap<String, String> = mutableMapOf(),
) {
	fun addValidation(
		key: String,
		value: String,
	) {
		validations[key] = value
	}
}
