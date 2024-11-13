package com.example.core.domain.user.converter

import com.example.core.enums.Role
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class UserSortConverter : AttributeConverter<Role, String> {
	override fun convertToDatabaseColumn(attribute: Role): String = attribute.displayName

	override fun convertToEntityAttribute(dbData: String): Role = Role.parse(dbData)
}
