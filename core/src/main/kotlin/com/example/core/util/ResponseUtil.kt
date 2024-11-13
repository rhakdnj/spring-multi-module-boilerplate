package com.example.core.util

import com.example.core.dto.response.ErrorResponse
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import kotlin.text.Charsets.UTF_8

fun writeHttpServletResponse(
	response: HttpServletResponse,
	status: HttpStatus,
	message: String,
) {
	response.contentType = MediaType.APPLICATION_JSON_VALUE
	response.characterEncoding = UTF_8.name()
	response.status = status.value()

	ObjectMapper().writeValue(
		response.outputStream,
		ErrorResponse(message),
	)
}
