package kr.rhakdnj.core.domain.user.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import kr.rhakdnj.core.enums.Role

@Converter(autoApply = true)
class UserSortConverter : AttributeConverter<Role, String> {
	override fun convertToDatabaseColumn(attribute: Role): String = attribute.displayName

	override fun convertToEntityAttribute(dbData: String): Role = Role.parse(dbData)
}
