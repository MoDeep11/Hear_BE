package modeep.hear.infrastructure.adapter.`in`.diary.dto.request

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.NotBlank
import modeep.hear.domain.diary.vo.DiaryImageStatus
import java.util.UUID

data class CallbackGenerationDiaryImageRequest(
    @JsonAlias("diary_id")
    val diaryId: UUID,
    val status: DiaryImageStatus,
    @JsonAlias("image_url")
    @field:NotBlank
    val imageUrl: String
)
