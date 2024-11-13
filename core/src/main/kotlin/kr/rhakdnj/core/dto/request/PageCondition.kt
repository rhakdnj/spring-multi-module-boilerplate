package kr.rhakdnj.core.dto.request

data class PageCondition(
	val page: Int = DEFAULT_PAGE,
	val size: Int = DEFAULT_SIZE,
) {
	companion object {
		const val DEFAULT_PAGE = 1
		const val DEFAULT_SIZE = 10
	}

	init {
		require(isValidPage(page)) { "Invalid page value: $page" }
		require(isValidSize(size)) { "Invalid size value: $size" }
	}

	private fun isValidPage(page: Int?): Boolean = page != null && page > 0

	private fun isValidSize(size: Int?): Boolean = size != null && size > 0 && size <= 100
}
