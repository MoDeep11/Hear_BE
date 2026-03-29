package modeep.hear.infrastructure.adapter.`in`.diary.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import modeep.hear.domain.common.vo.Emotion
import modeep.hear.domain.diary.vo.DiarySourceType
import java.util.UUID

data class CreateDiaryRequest(
    @field:NotBlank
    val content: String,
    val emotion: Emotion,
    @field:NotEmpty
    val tags: List<@NotBlank String>,
    val sourceType: DiarySourceType = DiarySourceType.AI_MADE,
    val chatId: UUID
)
