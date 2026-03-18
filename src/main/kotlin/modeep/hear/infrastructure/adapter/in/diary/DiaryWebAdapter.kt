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
    private val queryDiaryDetailUseCase: QueryDiaryDetailUseCase,
) : DiaryApiDocument {

    @GetMapping
    override fun getDiaries(
        @RequestParam imageType: DiarySourceType,
        @RequestParam hasPhoto: Boolean,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM") yearMonth: YearMonth,
        @RequestParam(defaultValue = "32") limit: Int,
        @RequestParam(defaultValue = "createdAt,desc") sort: String,
        @RequestParam tag: String?,
    ): ResponseEntity<ApiResult<QueryDiariesResponse>> {
        TODO("Not yet implemented")
    }

    @GetMapping("/{diary_id}")
    override fun getDiaryDetail(
        @PathVariable("diary_id") diaryId: UUID
    ): ResponseEntity<ApiResult<QueryDiaryDetailResponse>> {
        TODO("Not yet implemented")
    }
}