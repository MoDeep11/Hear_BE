package modeep.hear.domain.diary.port.`in`

import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.CreateDiaryResponse
import java.util.UUID

interface CreateDiaryUseCase {
    suspend fun execute(chatId: UUID): CreateDiaryResponse
}
