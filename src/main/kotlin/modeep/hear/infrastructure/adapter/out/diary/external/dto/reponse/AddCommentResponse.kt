package modeep.hear.infrastructure.adapter.out.diary.external.dto.reponse

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.NotBlank
import java.util.UUID

data class AddCommentResponse(
    @field:JsonAlias("diary_id")
    val diaryId: UUID,
    @field:JsonAlias("ai_comment")
    @field:NotBlank
    val aiComment: String
)
