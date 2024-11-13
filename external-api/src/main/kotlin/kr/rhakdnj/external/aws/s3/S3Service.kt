package kr.rhakdnj.external.aws.s3

import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.ObjectCannedAcl
import aws.sdk.kotlin.services.s3.model.PutObjectRequest
import aws.smithy.kotlin.runtime.content.ByteStream
import kr.rhakdnj.external.aws.FileService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
internal class S3Service(
	private val s3Properties: S3Properties,
	private val s3Client: S3Client,
) : FileService {
	override suspend fun upload(
		fileName: String,
		multipartFile: MultipartFile,
	): String {
		s3Client.putObject(
			PutObjectRequest {
				bucket = s3Properties.bucketName
				key = fileName
				body = ByteStream.fromBytes(multipartFile.bytes)
				acl = ObjectCannedAcl.PublicRead
			},
		)
		return getS3FilePath(fileName)
	}

	private fun getS3FilePath(fileName: String): String =
		"https://${s3Properties.bucketName}.s3.${s3Properties.region}.amazonaws.com/$fileName"
}
