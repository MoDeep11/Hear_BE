package modeep.hear.infrastructure.adapter.out.diary.external.dto.reponse

import jakarta.validation.constraints.NotBlank
import java.util.UUID

data class AddCommentResponse(
    val diaryId: UUID,
    @field:NotBlank
    val aiComment: String
)
