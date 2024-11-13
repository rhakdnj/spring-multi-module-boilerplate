package kr.rhakdnj.external.aws

import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.ses.SesClient
import kr.rhakdnj.external.aws.s3.S3Properties
import kr.rhakdnj.external.aws.ses.SesProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AwsClientConfig(
	private val s3Properties: S3Properties,
	private val sesProperties: SesProperties,
) {
	@Bean
	fun sesClient(): SesClient =
		SesClient {
			region = sesProperties.region
			credentialsProvider =
				StaticCredentialsProvider {
					accessKeyId = sesProperties.accessKey
					secretAccessKey = sesProperties.secretKey
				}
		}

	@Bean
	fun s3Client(): S3Client =
		S3Client {
			region = s3Properties.region
			credentialsProvider =
				StaticCredentialsProvider {
					accessKeyId = s3Properties.accessKey
					secretAccessKey = s3Properties.secretKey
				}
		}
}
