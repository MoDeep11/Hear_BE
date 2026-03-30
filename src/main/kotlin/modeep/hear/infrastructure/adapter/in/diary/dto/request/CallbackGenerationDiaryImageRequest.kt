package modeep.hear.infrastructure.adapter.`in`.diary.dto.request

import jakarta.validation.constraints.NotBlank
import modeep.hear.domain.diary.vo.DiaryImageStatus
import java.util.UUID

data class CallbackGenerationDiaryImageRequest(
    val diaryId: UUID,
    val status: DiaryImageStatus,
    @field:NotBlank
    val imageUrl: String
)