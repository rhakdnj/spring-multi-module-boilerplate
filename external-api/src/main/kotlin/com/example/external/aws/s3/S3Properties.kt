package com.example.external.aws.s3

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "aws.s3")
data class S3Properties(
    val region: String,
    val bucketName: String,
    val accessKey: String,
    val secretKey: String,
)
