package modeep.hear.domain.diary.service

import modeep.hear.domain.diary.port.`in`.GenerateDiaryImageUseCase
import modeep.hear.domain.diary.port.out.external.FetchDiaryImagePort
import modeep.hear.infrastructure.adapter.out.diary.external.dto.request.GenerateDiaryImageRequest
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GenerateDiaryImageService(
    private val diaryCommandService: DiaryCommandService,
    private val fetchDiaryImagePort: FetchDiaryImagePort,
) : GenerateDiaryImageUseCase {
    override suspend fun execute(
        diaryId: UUID,
    ) {
        val (diary, userId) = diaryCommandService.getDiaryWithUserId(diaryId)

        val req = GenerateDiaryImageRequest(
            diaryId = diaryId,
            userId = userId,
            emotion = diary.emotion,
            content = diary.content,
        )

        fetchDiaryImagePort.generateImage(req)
    }
}
