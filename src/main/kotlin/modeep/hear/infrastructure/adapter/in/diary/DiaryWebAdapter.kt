package modeep.hear.infrastructure.adapter.`in`.diary

import jakarta.validation.Valid
import modeep.hear.domain.diary.port.`in`.DeleteDiaryUseCase
import modeep.hear.domain.diary.port.`in`.QueryDiariesUseCase
import modeep.hear.domain.diary.port.`in`.QueryDiaryDetailUseCase
import modeep.hear.domain.diary.port.`in`.RecommendDiaryUseCase
import modeep.hear.domain.diary.port.`in`.UpdateDiaryContentUseCase
import modeep.hear.domain.diary.port.`in`.UploadDiaryImageUseCase
import modeep.hear.global.common.response.ApiResult
import modeep.hear.global.document.diary.DiaryApiDocument
import modeep.hear.infrastructure.adapter.`in`.diary.dto.request.QueryDiariesRequest
import modeep.hear.infrastructure.adapter.`in`.diary.dto.request.UpdateDiaryContentRequest
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.QueryDiariesResponse
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.QueryDiaryDetailResponse
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.RecommendDiaryResponse
import modeep.hear.infrastructure.adapter.`in`.storage.dto.request.UploadDiaryImageRequest
import modeep.hear.infrastructure.adapter.`in`.storage.dto.response.UploadDiaryImageResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/diaries")
class DiaryWebAdapter(
    private val queryDiariesUseCase: QueryDiariesUseCase,
    private val queryDiaryDetailUseCase: QueryDiaryDetailUseCase,
    private val deleteDiaryUseCase: DeleteDiaryUseCase,
    private val updateDiaryContentUseCase: UpdateDiaryContentUseCase,
    private val uploadDiaryImageUseCase: UploadDiaryImageUseCase,
    private val recommendDiaryUseCase: RecommendDiaryUseCase
) : DiaryApiDocument {

    @GetMapping
    override fun getDiaries(
        @ModelAttribute request: QueryDiariesRequest
    ): ResponseEntity<ApiResult<List<QueryDiariesResponse>>> {
        return ResponseEntity.ok(
            ApiResult(data = queryDiariesUseCase.execute(request))
        )
    }

    @GetMapping("/{diary_id}")
    override fun getDiaryDetail(
        @PathVariable("diary_id") diaryId: UUID
    ): ResponseEntity<ApiResult<QueryDiaryDetailResponse>> {
        return ResponseEntity.ok(
            ApiResult(data = queryDiaryDetailUseCase.execute(diaryId))
        )
    }

    @PatchMapping("/{diary_id}/images")
    override fun uploadDiaryImage(
        @PathVariable("diary_id") diaryId: UUID,
        @RequestBody @Valid
        requests: List<UploadDiaryImageRequest>
    ): ResponseEntity<ApiResult<List<UploadDiaryImageResponse>>> {
        return ResponseEntity.ok(
            ApiResult(
                data = uploadDiaryImageUseCase.execute(diaryId, requests)
            )
        )
    }

    @PatchMapping("/{diary_id}/content")
    override fun updateDiaryContent(
        @PathVariable("diary_id") diaryId: UUID,
        @RequestBody @Valid
        request: UpdateDiaryContentRequest
    ): ResponseEntity<ApiResult<Unit>> {
        updateDiaryContentUseCase.execute(diaryId, request)
        return ResponseEntity.ok(ApiResult())
    }

    @DeleteMapping("/{diary_id}")
    override fun deleteDiary(
        @PathVariable("diary_id") diaryId: UUID
    ): ResponseEntity<ApiResult<Unit>> {
        deleteDiaryUseCase.execute(diaryId)
        return ResponseEntity.ok(ApiResult())
    }

    @GetMapping("/recommendation")
    override fun recommendDiary(): ResponseEntity<ApiResult<RecommendDiaryResponse>> {
        return ResponseEntity.ok(
            ApiResult(
                data = recommendDiaryUseCase.execute()
            )
        )
    }
}
