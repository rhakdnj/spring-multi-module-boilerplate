package com.example.core.exception

import com.example.core.dto.response.BadRequestResponse
import com.example.core.dto.response.ErrorResponse
import com.example.core.logging.Logger.Companion.log
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.databind.exc.ValueInstantiationException
import org.springframework.beans.BeanInstantiationException
import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

const val BAD_REQUEST = "Bad Request Error"
const val INTERNAL_SERVER_ERROR = "Internal Server Error"

@RestControllerAdvice(basePackages = ["com.example"])
class ApiExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [HttpException::class])
    protected fun handleApiException(ex: HttpException): ResponseEntity<ErrorResponse> {
        log.error(ex) { ex.message }
        return ResponseEntity.status(ex.status).body(ErrorResponse(ex.message ?: INTERNAL_SERVER_ERROR))
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        when (val cause = ex.cause) {
            is MismatchedInputException -> {
                val badRequestResponse = BadRequestResponse(BAD_REQUEST)
                cause.path.forEach {
                    badRequestResponse.addValidation(it.fieldName, "${it.fieldName} 필수값입니다.")
                }
                return ResponseEntity.badRequest().body(badRequestResponse)
            }
            is ValueInstantiationException -> {
                return ResponseEntity.badRequest().body(ErrorResponse(cause.cause?.message ?: BAD_REQUEST))
            }
            else -> {
                return ResponseEntity.badRequest().body(ErrorResponse(BAD_REQUEST))
            }
        }
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        val badRequestResponse = BadRequestResponse(BAD_REQUEST)
        ex.bindingResult.fieldErrors.forEach {
            badRequestResponse.addValidation(it.field, it.defaultMessage!!)
        }
        return ResponseEntity.badRequest().body(badRequestResponse)
    }

    override fun handleTypeMismatch(
        ex: TypeMismatchException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        log.info(ex) { ex.message }
        val badRequestResponse = BadRequestResponse(BAD_REQUEST)
        badRequestResponse.addValidation(ex.propertyName ?: "", ex.rootCause?.message ?: BAD_REQUEST)
        return ResponseEntity.badRequest().body(badRequestResponse)
    }

    @ExceptionHandler(value = [IllegalArgumentException::class])
    protected fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        log.warn(ex) { ex.message }
        return ResponseEntity.badRequest().body(ErrorResponse(ex.message ?: BAD_REQUEST))
    }

    @ExceptionHandler(value = [BeanInstantiationException::class])
    protected fun handleBeanInstantiationException(ex: BeanInstantiationException): ResponseEntity<ErrorResponse> {
        if (ex.cause is IllegalArgumentException) {
            return ResponseEntity.badRequest().body(ErrorResponse(ex.cause!!.message!!))
        }
        log.warn { "${ex.cause?.message ?: ex.message}" }
        return ResponseEntity.internalServerError().body(ErrorResponse(INTERNAL_SERVER_ERROR))
    }

    @ExceptionHandler(value = [Exception::class])
    protected fun handleException(ex: Exception): ResponseEntity<ErrorResponse> {
        log.error(ex) { ex.message }

        if (ex is AccessDeniedException || ex is AuthenticationCredentialsNotFoundException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponse("API 권한이 없습니다."))
        }
        return ResponseEntity.internalServerError().body(ErrorResponse(INTERNAL_SERVER_ERROR))
    }
}
