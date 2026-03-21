package modeep.hear.infrastructure.adapter.`in`.diary.dto.request

import jakarta.validation.constraints.NotBlank

data class UpdateDiaryContentRequest(
    @field:NotBlank
    val content: String
)
