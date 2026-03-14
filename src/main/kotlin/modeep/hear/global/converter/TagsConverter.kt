package modeep.hear.global.converter

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.Converter

@Converter
class TagsConverter(objectMapper: ObjectMapper) :
    JsonConverter<List<*>>(List::class.java, objectMapper)
