package modeep.hear.global.converter.deprecated

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.AttributeConverter
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.global.error.exception.GlobalErrorCode

@Deprecated("Not used anymore")
abstract class JsonConverter<T>(
    private val typeReference: TypeReference<T>,
    private val objectMapper: ObjectMapper
) : AttributeConverter<T, String> {

    override fun convertToDatabaseColumn(attribute: T?): String? {
        return attribute?.let {
            try {
                objectMapper.writeValueAsString(it)
            } catch (e: Exception) {
                throw BusinessException(
                    GlobalErrorCode.JSON_CONVERSION_ERROR,
                    "객체를 JSON 문자열로 변환하는데 실패했습니다: ${typeReference.type.typeName}",
                    cause = e
                )
            }
        }
    }

    override fun convertToEntityAttribute(dbData: String?): T? {
        if (dbData.isNullOrBlank()) return null

        return try {
            objectMapper.readValue(dbData, typeReference)
        } catch (e: Exception) {
            throw BusinessException(
                GlobalErrorCode.JSON_CONVERSION_ERROR,
                "JSON을 객체로 변환하는데 실패했습니다: ${typeReference.type.typeName}",
                cause = e
            )
        }
    }
}
