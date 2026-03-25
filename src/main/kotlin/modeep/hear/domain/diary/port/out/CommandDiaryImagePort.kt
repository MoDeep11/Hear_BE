package modeep.hear.domain.diary.port.out

import modeep.hear.domain.diary.model.DiaryImage
import java.util.UUID

interface CommandDiaryImagePort {
    fun saveAll(diaryImages: List<DiaryImage>)

    fun delete(diaryImageId: UUID)
}
