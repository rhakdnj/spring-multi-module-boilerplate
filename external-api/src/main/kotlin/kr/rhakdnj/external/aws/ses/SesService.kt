package kr.rhakdnj.external.aws.ses

import aws.sdk.kotlin.services.ses.SesClient
import aws.sdk.kotlin.services.ses.model.Body
import aws.sdk.kotlin.services.ses.model.Content
import aws.sdk.kotlin.services.ses.model.Destination
import aws.sdk.kotlin.services.ses.model.Message
import aws.sdk.kotlin.services.ses.model.SendEmailRequest
import kr.rhakdnj.external.aws.EmailService
import org.springframework.stereotype.Service

@Service
internal class SesService(
	private val sesProperties: SesProperties,
	private val sesClient: SesClient,
) : EmailService {
	override suspend fun sendMail(
		subject: String,
		body: String,
		toAddresses: List<String>,
	) {
		sesClient.sendEmail(createEmailRequest(toAddresses, subject, body))
	}

	private fun createEmailRequest(
		toAddresses: List<String>,
		subject: String,
		body: String,
	) = SendEmailRequest {
		destination =
			Destination {
				this.toAddresses = toAddresses
			}
		message =
			Message {
				this.subject =
					Content {
						charset = "UTF-8"
						data = subject
					}
				this.body =
					Body {
						html =
							Content {
								charset = "UTF-8"
								data = body
							}
					}
			}
		source = sesProperties.sourceAddress
	}
}
