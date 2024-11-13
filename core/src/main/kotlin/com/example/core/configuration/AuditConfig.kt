package com.example.core.configuration

import com.example.core.util.findUserIdFromSecurityContextHolder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.Optional

@Configuration
@EnableJpaAuditing
class AuditConfig {
	@Bean
	fun auditorAware(): AuditorAware<Long> =
		AuditorAware {
			Optional.ofNullable(findUserIdFromSecurityContextHolder()?.toLongOrNull())
		}
}
