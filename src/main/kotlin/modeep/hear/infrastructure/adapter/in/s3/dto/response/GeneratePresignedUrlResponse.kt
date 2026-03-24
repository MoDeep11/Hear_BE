package modeep.hear.infrastructure.adapter.`in`.s3.dto.response

data class GeneratePresignedUrlResponse(
    val url: String,
    val key: String
)
