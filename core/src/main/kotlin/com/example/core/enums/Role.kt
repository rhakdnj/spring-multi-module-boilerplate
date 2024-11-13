package com.example.core.enums

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class Role(
	@get:JsonValue val displayName: String,
) {
	ROLE_USER("U"),
	ROLE_ADMIN("A"),
	;

	companion object {
		@JvmStatic
		@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
		fun parse(displayName: String): Role =
			entries.find {
				it.displayName == displayName
			} ?: throw IllegalArgumentException("알 수 없는 Role: $displayName 입니다.")

		fun from(authority: String): Role =
			entries.find {
				it.name == authority
			} ?: throw IllegalArgumentException("${authority}에 해당하는 Role이 존재하지 않습니다.")
	}
}
