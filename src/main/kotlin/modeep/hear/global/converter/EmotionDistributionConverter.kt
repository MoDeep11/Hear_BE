package modeep.hear.global.converter

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.Converter
import modeep.hear.domain.common.model.emotion.EmotionDistribution

@Converter
class EmotionDistributionConverter(
    objectMapper: ObjectMapper,
) : JsonConverter<EmotionDistribution>(EmotionDistribution::class.java, objectMapper)