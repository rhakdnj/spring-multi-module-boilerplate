package kr.rhakdnj.external.aws

interface EmailService {
	suspend fun sendMail(
		subject: String,
		body: String,
		toAddresses: List<String>,
	)
}
