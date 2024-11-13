package com.example.core.domain.common.vo

import jakarta.persistence.Embeddable

@Embeddable
class TranscriptUrl(
    val value: String,
) {
    init {
        require(TRANSCRIPT_URL_REGEX.matches(value)) { "증명 자료 URL이 올바르지 않습니다." }
    }

    companion object {
        private val TRANSCRIPT_URL_REGEX = Regex("^(http(s?):)([/|.|\\w|\\s|-])*\\.png\$")
    }
}
