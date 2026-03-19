package modeep.hear.infrastructure.adapter.`in`.diary

import modeep.hear.domain.diary.port.`in`.QueryDiariesUseCase
import modeep.hear.domain.diary.port.`in`.QueryDiaryDetailUseCase
import modeep.hear.global.common.response.ApiResult
import modeep.hear.global.document.diary.DiaryApiDocument
import modeep.hear.infrastructure.adapter.`in`.diary.dto.request.QueryDiariesRequest
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.QueryDiariesResponse
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.QueryDiaryDetailResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/diaries")
class DiaryWebAdapter(
    private val queryDiariesUseCase: QueryDiariesUseCase,
    private val queryDiaryDetailUseCase: QueryDiaryDetailUseCase
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
}
