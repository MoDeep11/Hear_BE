package modeep.hear.domain.diary.port.out

import java.util.UUID

interface CommandDiaryImagePort {
    fun delete(diaryImageId: UUID)
}
