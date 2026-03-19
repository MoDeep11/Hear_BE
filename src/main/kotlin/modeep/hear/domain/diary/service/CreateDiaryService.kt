package modeep.hear.domain.diary.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.diary.port.`in`.CreateDiaryUseCase
import modeep.hear.domain.diary.port.out.DiaryPort
import modeep.hear.infrastructure.adapter.`in`.diary.dto.request.CreateDiaryRequest
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.CreateDiaryResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CreateDiaryService(
    private val diaryPort: DiaryPort,
    private val securityPort: SecurityPort
) : CreateDiaryUseCase {
    override fun execute(request: CreateDiaryRequest): CreateDiaryResponse {
        TODO()
    }
}
