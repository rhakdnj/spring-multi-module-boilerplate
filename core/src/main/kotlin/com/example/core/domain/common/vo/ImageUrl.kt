package com.example.core.domain.common.vo

import jakarta.persistence.Embeddable

@Embeddable
class ImageUrl(
	val value: String,
) {
	init {
		require(IMAGE_URL_REGEX.matches(value)) { "이미지 URL이 올바르지 않습니다." }
	}

	companion object {
		private val IMAGE_URL_REGEX = Regex("^(http(s?):)([/|.|\\w|\\s|-])*\\.png\$")
	}
}
