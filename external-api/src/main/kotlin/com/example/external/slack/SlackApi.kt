package com.example.external.slack

import com.example.core.logging.Logger.Companion.log
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class SlackApi(
	@Qualifier("slackRestClient")
	private val restClient: RestClient,
) {
	fun good(
		webhookUrl: String,
		message: String,
	) {
		log.info { "webhookUrl: $webhookUrl, message: $message" }
		val slackRequest = SlackRequest.good(message)
		send(webhookUrl, slackRequest)
	}

	fun warning(
		webhookUrl: String,
		message: String,
	) {
		log.warn { "webhookUrl: $webhookUrl, message: $message" }
		val slackRequest = SlackRequest.warning(message)
		send(webhookUrl, slackRequest)
	}

	fun danger(
		webhookUrl: String,
		message: String,
	) {
		log.error { "webhookUrl: $webhookUrl, message: $message" }
		val slackRequest = SlackRequest.danger(message)
		send(webhookUrl, slackRequest)
	}

	private fun send(
		webhookUrl: String,
		slackRequest: SlackRequest,
	) {
		restClient
			.post()
			.uri(webhookUrl)
			.contentType(MediaType.APPLICATION_JSON)
			.body(slackRequest)
			.retrieve()
	}
}
