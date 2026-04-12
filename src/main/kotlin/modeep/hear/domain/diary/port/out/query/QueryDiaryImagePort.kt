package modeep.hear.domain.diary.port.out.query

import modeep.hear.domain.diary.model.DiaryImage
import java.util.UUID

interface QueryDiaryImagePort {
    fun findAllByChatId(chatId: UUID): List<DiaryImage>

    fun findAllByDiaryId(diaryId: UUID): List<DiaryImage>
}
