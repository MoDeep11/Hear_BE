package modeep.hear.infrastructure.adapter.out.diary.external.dto.request

import modeep.hear.domain.common.vo.Emotion
import java.util.UUID

data class AddCommentRequest(
    val diaryId: UUID,
    val userId: UUID,
    val nickname: String,
    val emotion: Emotion,
    val content: String
)
