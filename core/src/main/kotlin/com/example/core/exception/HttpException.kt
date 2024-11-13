package com.example.core.exception

import org.springframework.http.HttpStatus

open class HttpException(
	val status: HttpStatus,
	message: String,
	cause: Throwable? = null,
) : RuntimeException(message, cause)

class BadRequestException : HttpException {
	constructor(message: String) : super(HttpStatus.BAD_REQUEST, message)
	constructor(message: String, cause: Throwable) : super(
		HttpStatus.BAD_REQUEST,
		message,
		cause,
	)
}

class NotFoundException : HttpException {
	constructor(message: String) : super(HttpStatus.NOT_FOUND, message)
	constructor(message: String, cause: Throwable) : super(HttpStatus.NOT_FOUND, message, cause)
}

class ConflictException : HttpException {
	constructor(message: String) : super(HttpStatus.CONFLICT, message)
	constructor(message: String, cause: Throwable) : super(HttpStatus.CONFLICT, message, cause)
}

class UnauthorizedException : HttpException {
	constructor(message: String) : super(HttpStatus.UNAUTHORIZED, message)
	constructor(message: String, cause: Throwable) : super(HttpStatus.UNAUTHORIZED, message, cause)
}

class ForbiddenException : HttpException {
	constructor(message: String) : super(HttpStatus.FORBIDDEN, message)
	constructor(message: String, cause: Throwable) : super(HttpStatus.FORBIDDEN, message, cause)
}
