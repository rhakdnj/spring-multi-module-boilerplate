package kr.rhakdnj.external.slack

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class SlackClientConfig {
	@Bean
	fun slackRestClient(): RestClient = RestClient.create()
}
