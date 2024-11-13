package com.example.external.aws.ses

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "aws.ses")
data class SesProperties(
    val region: String,
    val accessKey: String,
    val secretKey: String,
    val sourceAddress: String,
)
