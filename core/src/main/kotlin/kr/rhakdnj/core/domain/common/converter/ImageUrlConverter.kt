package kr.rhakdnj.core.domain.common.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import kr.rhakdnj.core.domain.common.vo.ImageUrl

@Converter(autoApply = true)
class ImageUrlConverter : AttributeConverter<ImageUrl, String> {
	override fun convertToDatabaseColumn(attribute: ImageUrl): String = attribute.value

	override fun convertToEntityAttribute(dbData: String?): ImageUrl? = dbData?.let { ImageUrl(it) }
}
