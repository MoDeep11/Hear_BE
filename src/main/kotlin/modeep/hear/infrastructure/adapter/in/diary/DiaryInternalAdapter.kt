package modeep.hear.infrastructure.adapter.`in`.diary

import jakarta.validation.Valid
import modeep.hear.domain.diary.port.`in`.CallbackGenerationDiaryImageUseCase
import modeep.hear.domain.diary.port.`in`.CreateDiaryUseCase
import modeep.hear.global.common.response.ApiResult
import modeep.hear.global.document.diary.DiaryInternalApiDocument
import modeep.hear.infrastructure.adapter.`in`.diary.dto.request.CallbackGenerationDiaryImageRequest
import modeep.hear.infrastructure.adapter.`in`.diary.dto.request.CreateDiaryRequest
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.CreateDiaryResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/internal/v1/diaries")
class DiaryInternalAdapter(
    private val createDiaryUseCase: CreateDiaryUseCase,
    private val callbackGenerationDiaryImageUseCase: CallbackGenerationDiaryImageUseCase
) : DiaryInternalApiDocument {
    @PostMapping
    override suspend fun createDiary(
        @RequestBody @Valid
        request: CreateDiaryRequest
    ): ResponseEntity<ApiResult<CreateDiaryResponse>> {
        val res = createDiaryUseCase.execute(request)
        return ResponseEntity.ok(ApiResult(data = res))
    }

    @PatchMapping("/{diary_id}/images")
    override suspend fun callbackGenerationDiaryImage(
        @PathVariable("diary_id") diaryId: UUID,
        @RequestBody @Valid
        request: CallbackGenerationDiaryImageRequest
    ): ResponseEntity<ApiResult<Unit>> {
        callbackGenerationDiaryImageUseCase.execute(diaryId, request)
        return ResponseEntity.ok(ApiResult())
    }
}
