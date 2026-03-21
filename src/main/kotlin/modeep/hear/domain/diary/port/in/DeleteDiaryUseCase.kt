package modeep.hear.domain.diary.port.`in`

import java.util.UUID

interface DeleteDiaryUseCase {
    fun execute(diaryId: UUID)
}
