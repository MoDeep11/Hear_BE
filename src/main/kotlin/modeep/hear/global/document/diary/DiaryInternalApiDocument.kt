package modeep.hear.global.document.diary

import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import modeep.hear.global.common.response.ApiResult
import modeep.hear.infrastructure.adapter.`in`.diary.dto.request.CallbackGenerationDiaryImageRequest
import modeep.hear.infrastructure.adapter.`in`.diary.dto.request.CreateDiaryRequest
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.CreateDiaryResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import java.util.UUID

@Tag(name = "Diary Internal", description = "Diary 도메인 내부 통신용 API")
interface DiaryInternalApiDocument {
    suspend fun createDiary(
        @RequestBody @Valid
        request: CreateDiaryRequest
    ): ResponseEntity<ApiResult<CreateDiaryResponse>>

    suspend fun callbackGenerationDiaryImage(
        diaryId: UUID,
        @RequestBody @Valid
        request: CallbackGenerationDiaryImageRequest
    ): ResponseEntity<ApiResult<Unit>>
}
