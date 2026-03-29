package modeep.hear.domain.diary.port.dto.command

import modeep.hear.domain.common.vo.Emotion
import java.util.UUID

data class CreateDiaryAiCommentCommand(
    val diaryId: UUID,
    val userId: UUID,
    val nickname: String,
    val emotion: Emotion,
    val content: String,
    val imageUrls: List<String>
)