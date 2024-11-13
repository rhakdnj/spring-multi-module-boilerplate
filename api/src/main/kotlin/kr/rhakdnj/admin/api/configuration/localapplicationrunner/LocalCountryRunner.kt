package kr.rhakdnj.admin.api.configuration.localapplicationrunner

import kr.rhakdnj.core.domain.country.Country
import kr.rhakdnj.core.domain.country.repository.CountryRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("local")
class LocalCountryRunner(
	private val countryRepository: CountryRepository,
) {
	fun init() {
		countryRepository.saveAll(
			listOf(
				Country(
					name = "대한민국",
				),
				Country(
					name = "미국",
				),
			),
		)
	}
}
