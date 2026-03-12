package modeep.hear.global.common.response

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

data class ErrorResponse(
    val code: String,
    val message: String,
    val timestamp: LocalDateTime = LocalDateTime.now(),

    @field:JsonInclude(JsonInclude.Include.NON_NULL)
    val errors: List<FieldError>? = null
) {
    data class FieldError(
        val field: String,
        val value: String,
        val reason: String
    )
}