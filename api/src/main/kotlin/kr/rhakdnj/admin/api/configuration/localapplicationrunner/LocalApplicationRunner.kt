package kr.rhakdnj.admin.api.configuration.localapplicationrunner

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("local")
class LocalApplicationRunner(
	private val userRunner: kr.rhakdnj.admin.api.configuration.localapplicationrunner.LocalUserRunner,
	private val countryRunner: kr.rhakdnj.admin.api.configuration.localapplicationrunner.LocalCountryRunner,
	private val universityRunner: kr.rhakdnj.admin.api.configuration.localapplicationrunner.LocalUniversityRunner,
) : ApplicationRunner {
	override fun run(args: ApplicationArguments?) {
		userRunner.init()
		countryRunner.init()
		universityRunner.init()
	}
}
