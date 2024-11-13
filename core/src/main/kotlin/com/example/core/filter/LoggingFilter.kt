package com.example.core.filter

import com.example.core.logging.Logger.Companion.log
import jakarta.servlet.FilterChain
import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.http.MediaType
import org.springframework.util.StreamUtils
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingResponseWrapper
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.UUID

class LoggingFilter : OncePerRequestFilter() {
	override fun doFilterInternal(
		request: HttpServletRequest,
		response: HttpServletResponse,
		filterChain: FilterChain,
	) {
		MDC.put("traceId", UUID.randomUUID().toString())
		if (
			isAsyncDispatch(request) ||
			request.requestURI.contains("upload") ||
			request.requestURI.contains("actuator") ||
			request.requestURI.contains("files") ||
			request.requestURI.contains("actuator") ||
			request.requestURI.contains("swagger") ||
			request.requestURI.contains("api-docs")
		) {
			filterChain.doFilter(request, response)
		} else {
			doFilterWrapped(
				RequestWrapper(request),
				ResponseWrapper(response),
				filterChain,
			)
		}
		MDC.clear()
	}

	protected fun doFilterWrapped(
		requestWrapper: RequestWrapper,
		responseWrapper: ResponseWrapper,
		filterChain: FilterChain,
	) {
		try {
			logRequest(requestWrapper)
			filterChain.doFilter(requestWrapper, responseWrapper)
		} finally {
			logResponse(responseWrapper)
			responseWrapper.copyBodyToResponse()
		}
	}

	private fun logRequest(requestWrapper: RequestWrapper) {
		val queryString = requestWrapper.queryString

		log.info {
			"Request : ${requestWrapper.method} " +
				"uri[${if (queryString == null) requestWrapper.requestURI else requestWrapper.requestURI + "?" + queryString}] " +
				"content-type=[${requestWrapper.contentType}]"
		}

		logPayload("Request", requestWrapper.contentType, requestWrapper.inputStream)
	}

	private fun logResponse(responseWrapper: ResponseWrapper) {
		logPayload("Response", responseWrapper.contentType, responseWrapper.contentInputStream)
	}

	private fun logPayload(
		prefix: String,
		contentType: String?,
		inputStream: InputStream,
	) {
		val content = StreamUtils.copyToByteArray(inputStream)
		val mediaType = contentType?.let { MediaType.valueOf(it) } ?: MediaType.APPLICATION_JSON
		val payload = getPayload(mediaType, content)
		log.info { "$prefix Payload: $payload" }
	}

	private fun getPayload(
		mediaType: MediaType,
		content: ByteArray,
	) = if (isVisible(mediaType)) {
		"\"password\":\\s*\"[^\"]*\""
			.toRegex()
			.replace(String(content), "\"password\":\"********\"")
	} else {
		"Binary Contents"
	}

	private fun isVisible(mediaType: MediaType): Boolean =
		VISIBLE_TYPES.stream().anyMatch {
			it.includes(mediaType)
		}

	companion object {
		private val VISIBLE_TYPES: List<MediaType> =
			listOf(
				MediaType.valueOf("text/*"),
				MediaType.APPLICATION_FORM_URLENCODED,
				MediaType.APPLICATION_JSON,
				MediaType.APPLICATION_XML,
				MediaType.valueOf("application/*+json"),
				MediaType.valueOf("application/*+xml"),
				MediaType.MULTIPART_FORM_DATA,
			)
	}
}

class RequestWrapper(
	request: HttpServletRequest,
) : HttpServletRequestWrapper(request) {
	private var cachedInputStream: ByteArray

	init {
		val reqInputStream = request.inputStream
		cachedInputStream = StreamUtils.copyToByteArray(reqInputStream)
	}

	override fun getInputStream(): ServletInputStream {
		return object : ServletInputStream() {
			private val cachedBodyInputStream = ByteArrayInputStream(cachedInputStream)

			override fun read(): Int = cachedBodyInputStream.read()

			override fun isFinished() =
				runCatching {
					return cachedBodyInputStream.available() == 0
				}.onFailure {
					log.error { it.message }
				}.getOrDefault(false)

			override fun isReady(): Boolean = true

			override fun setReadListener(listener: ReadListener?): Unit = throw java.lang.UnsupportedOperationException()
		}
	}
}

class ResponseWrapper(
	response: HttpServletResponse,
) : ContentCachingResponseWrapper(response)
