package modeep.hear.domain.diary.port.out

import modeep.hear.domain.diary.model.DiaryImage
import java.util.UUID

interface QueryDiaryImagePort {
    fun findAllBySessionId(sessionId: UUID): List<DiaryImage>
}
