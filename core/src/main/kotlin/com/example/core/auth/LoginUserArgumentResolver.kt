package com.example.core.auth

import com.example.core.exception.UnauthorizedException
import com.example.core.util.findRolesFromSecurityContextHolder
import com.example.core.util.findUserIdFromSecurityContextHolder
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import java.util.UUID

@Component
class LoginUserArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        parameter.getParameterAnnotation(LoginUser::class.java) ?: return false
        return parameter.parameterType.isAssignableFrom(TokenPayload::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Any {
        val userId = findUserIdFromSecurityContextHolder()
        val role = findRolesFromSecurityContextHolder()

        if (userId == null || role == null) {
            throw UnauthorizedException("로그인이 필요합니다.")
        }
        return TokenPayload(UUID.fromString(userId.toString()), role)
    }
}
