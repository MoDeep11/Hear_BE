package modeep.hear.global.common.converter.deprecated

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.Converter
import modeep.hear.domain.common.vo.EmotionDistribution

@Deprecated("Not used anymore")
@Converter
class EmotionDistributionConverter(
    objectMapper: ObjectMapper
) : JsonConverter<EmotionDistribution>(
    object : TypeReference<EmotionDistribution>() {},
    objectMapper
)
