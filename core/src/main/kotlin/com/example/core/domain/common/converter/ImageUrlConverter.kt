package com.example.core.domain.common.converter

import com.example.core.domain.common.vo.ImageUrl
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class ImageUrlConverter : AttributeConverter<ImageUrl, String> {
    override fun convertToDatabaseColumn(attribute: ImageUrl): String = attribute.value

    override fun convertToEntityAttribute(dbData: String?): ImageUrl? = dbData?.let { ImageUrl(it) }
}
