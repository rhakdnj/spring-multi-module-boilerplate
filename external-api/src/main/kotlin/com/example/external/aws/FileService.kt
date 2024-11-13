package com.example.external.aws

import org.springframework.web.multipart.MultipartFile

interface FileService {
	suspend fun upload(
		fileName: String,
		multipartFile: MultipartFile,
	): String
}
