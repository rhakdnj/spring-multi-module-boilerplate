package kr.rhakdnj.admin.api.configuration

import kr.rhakdnj.core.auth.LoginUserArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfiguration(
	private val loginUserArgumentResolver: LoginUserArgumentResolver,
) : WebMvcConfigurer {
	override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
		resolvers.add(loginUserArgumentResolver)
	}
}
