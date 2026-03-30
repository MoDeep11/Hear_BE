package modeep.hear.domain.diary.port.`in`.deprecated

import java.util.UUID

@Deprecated("Not used anymore")
interface GenerateDiaryImageUseCase {
    suspend fun execute(
        diaryId: UUID
    )
}