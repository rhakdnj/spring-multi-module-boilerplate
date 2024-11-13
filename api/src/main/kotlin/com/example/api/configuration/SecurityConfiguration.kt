package com.example.api.configuration

import com.example.core.auth.AuthTokenProvider
import com.example.core.auth.filter.TokenAuthenticationFilter
import com.example.core.filter.LoggingFilter
import com.example.core.logging.Logger.Companion.log
import com.example.core.util.writeHttpServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.CorsUtils
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true,
)
class SecurityConfiguration(
    private val corsProperties: CorsProperties,
    private val authTokenProvider: AuthTokenProvider,
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.invoke {
            headers { frameOptions { disable() } }
            csrf { disable() }
            anonymous { disable() }
            formLogin { disable() }
            httpBasic { disable() }
            logout { disable() }
            sessionManagement { SessionCreationPolicy.STATELESS }

            cors {
                corsConfigurationSource()
            }

            authorizeHttpRequests {
                authorize(CorsUtils::isPreFlightRequest, permitAll)

                authorize(anyRequest, permitAll)
            }

            exceptionHandling {
                authenticationEntryPoint =
                    RestAuthenticationEntryPoint()
                accessDeniedHandler =
                    TokenAccessDeniedHandler()
            }

            addFilterBefore<UsernamePasswordAuthenticationFilter>(tokenAuthenticationFilter())
            addFilterBefore<TokenAuthenticationFilter>(loggingFilter())
        }

        return http.build()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun tokenAuthenticationFilter() = TokenAuthenticationFilter(authTokenProvider)

    @Bean
    fun loggingFilter() = LoggingFilter()

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val corsConfigSource = UrlBasedCorsConfigurationSource()
        val corsConfig = CorsConfiguration()
        corsConfig.allowedOrigins = corsProperties.allowedOrigins
        corsConfig.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        corsConfig.allowedHeaders = listOf("*")
        corsConfig.allowCredentials = true
        corsConfig.maxAge = 3_600
        corsConfigSource.registerCorsConfiguration("/**", corsConfig)
        return corsConfigSource
    }
}

class RestAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException,
    ) {
        log.info { "Authentication error : ${authException.message}" }
        writeHttpServletResponse(
            response = response,
            status = HttpStatus.UNAUTHORIZED,
            message = "인증되지 않은 사용자입니다.",
        )
    }
}

class TokenAccessDeniedHandler : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException,
    ) {
        log.info { "Access Denied message : ${accessDeniedException.message}" }
        writeHttpServletResponse(
            response = response,
            status = HttpStatus.UNAUTHORIZED,
            message = "인증되지 않은 사용자입니다.",
        )
    }
}
