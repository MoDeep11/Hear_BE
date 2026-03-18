package modeep.hear.infrastructure.adapter.`in`.diary

import modeep.hear.domain.diary.port.`in`.QueryDiariesUseCase
import modeep.hear.domain.diary.port.`in`.QueryDiaryDetailUseCase
import modeep.hear.domain.diary.vo.DiarySourceType
import modeep.hear.global.common.response.ApiResult
import modeep.hear.global.document.diary.DiaryApiDocument
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.QueryDiariesResponse
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.QueryDiaryDetailResponse
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.YearMonth
import java.util.UUID

@RestController
@RequestMapping("/api/v1/diaries")
class DiaryWebAdapter(
    private val queryDiariesUseCase: QueryDiariesUseCase,
    private val queryDiaryDetailUseCase: QueryDiaryDetailUseCase
) : DiaryApiDocument {

    companion object {
        const val DEFAULT_HAS_PHOTO = "true"
    }

    @GetMapping
    override fun getDiaries(
        @RequestParam(required = false, defaultValue = DiarySourceType.DEFAULT_TYPE) imageType: DiarySourceType,
        @RequestParam(required = false, defaultValue = DEFAULT_HAS_PHOTO) hasPhoto: Boolean,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM") yearMonth: YearMonth?,
        @RequestParam(required = false, defaultValue = "32") limit: Int,
        @RequestParam(required = false, defaultValue = "createdAt,desc") sort: String,
        @RequestParam(required = false) tag: String?
    ): ResponseEntity<ApiResult<List<QueryDiariesResponse>>> {
        val resolvedYearMonth = yearMonth ?: YearMonth.now()
        val diaries = queryDiariesUseCase.execute(imageType, hasPhoto, resolvedYearMonth, limit, sort, tag)
        return ResponseEntity.ok(
            ApiResult(
                data = diaries
            )
        )
    }

    @GetMapping("/{diary_id}")
    override fun getDiaryDetail(
        @PathVariable("diary_id") diaryId: UUID
    ): ResponseEntity<ApiResult<QueryDiaryDetailResponse>> {
        val diary = queryDiaryDetailUseCase.execute(diaryId)
        return ResponseEntity.ok(
            ApiResult(
                data = diary
            )
        )
    }
}
