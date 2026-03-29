package modeep.hear.infrastructure.adapter.`in`.diary

import modeep.hear.domain.diary.port.`in`.CreateDiaryUseCase
import modeep.hear.global.common.response.ApiResult
import modeep.hear.global.document.diary.DiaryInternalApiDocument
import modeep.hear.infrastructure.adapter.`in`.diary.dto.request.CreateDiaryRequest
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.CreateDiaryResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/internal/v1/diaries")
class DiaryInternalAdapter(
    private val createDiaryUseCase: CreateDiaryUseCase
) : DiaryInternalApiDocument {
    @PostMapping
    override suspend fun createDiary(request: CreateDiaryRequest): ResponseEntity<ApiResult<CreateDiaryResponse>> {
        val res = createDiaryUseCase.execute(request)
        return ResponseEntity.ok(ApiResult(data = res))
    }
}
