package modeep.hear.domain.diary.port.out

import modeep.hear.domain.diary.model.DiaryImage
import java.util.UUID

interface QueryDiaryImagePort {
    fun findAllByChatId(sessionId: UUID): List<DiaryImage>
}
