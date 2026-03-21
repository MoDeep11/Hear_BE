package modeep.hear.domain.diary.port.out

import modeep.hear.domain.diary.model.Diary
import java.util.UUID

interface CommandDiaryPort {
    fun save(diary: Diary)

    fun deleteById(diaryId: UUID)
}
