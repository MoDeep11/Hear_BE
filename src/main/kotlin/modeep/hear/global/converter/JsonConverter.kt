package modeep.hear.global.converter

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.AttributeConverter
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.global.error.exception.GlobalErrorCode

abstract class JsonConverter<T>(
    private val clazz: Class<T>,
    private val objectMapper: ObjectMapper
) : AttributeConverter<T, String> {

    override fun convertToDatabaseColumn(attribute: T?): String? {
        return attribute?.let {
            try {
                objectMapper.writeValueAsString(it)
            } catch (e: Exception) {
                throw BusinessException(
                    GlobalErrorCode.JSON_CONVERSION_ERROR,
                    "객체를 JSON 문자열로 변환하는데 실패했습니다: ${clazz.simpleName}",
                    e.message
                )
            }
        }
    }

    override fun convertToEntityAttribute(dbData: String?): T? {
        return dbData?.let {
            try {
                objectMapper.readValue(it, clazz)
            } catch (e: Exception) {
                throw BusinessException(
                    GlobalErrorCode.JSON_CONVERSION_ERROR,
                    "JSON을 객체로 변환하는데 실패했습니다: ${clazz.simpleName}",
                    e.message
                )
            }
        }
    }
}
