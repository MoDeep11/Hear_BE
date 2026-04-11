package modeep.hear.global.common.converter.deprecated

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.Converter

@Deprecated("Not used anymore")
@Converter
class TagsConverter(
    objectMapper: ObjectMapper
) : JsonConverter<List<String>>(
    object : TypeReference<List<String>>() {},
    objectMapper
)
