package modeep.hear.infrastructure.adapter.out.diary.external.dto.request

import modeep.hear.domain.common.vo.Emotion
import modeep.hear.domain.diary.event.GenerateDiaryImageEvent
import java.util.UUID

data class GenerateDiaryImageRequest(
    val diaryId: UUID,
    val userId: UUID,
    val emotion: Emotion,
    val content: String
) {
    companion object {
        fun from(event: GenerateDiaryImageEvent) =
            GenerateDiaryImageRequest(
                diaryId = event.diary.id,
                userId = event.userId,
                emotion = event.diary.emotion,
                content = event.diary.content
            )
    }
}
