package kr.rhakdnj.core.exception

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.databind.exc.ValueInstantiationException
import kr.rhakdnj.core.dto.response.BadRequestResponse
import kr.rhakdnj.core.dto.response.ErrorResponse
import kr.rhakdnj.core.logging.Logger.Companion.log
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

const val BAD_REQUEST = "올바르지 않은 요청입니다."
const val INTERNAL_SERVER_ERROR = "서버 내부 요류입니다."

@RestControllerAdvice(basePackages = ["kr.rhakdnj"])
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
				// note: field 3개를 필요할 때, 1개만 들어오면 필드의 필수값이라고 알려주기 위함
				// - 다만, 2개를 알려주지 못함, 오직 하나씩 알려줌
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
