package modeep.hear.domain.diary.port.`in`

import java.util.UUID

interface GenerateDiaryImageUseCase {
    suspend fun execute(
        diaryId: UUID
    )
}
