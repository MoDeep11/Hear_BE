package modeep.hear.global.document.diary

import io.swagger.v3.oas.annotations.tags.Tag
import modeep.hear.global.common.response.ApiResult
import modeep.hear.infrastructure.adapter.`in`.diary.dto.request.QueryDiariesRequest
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.QueryDiariesResponse
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.QueryDiaryDetailResponse
import org.springframework.http.ResponseEntity
import java.util.UUID

@Tag(name = "Diary", description = "Diary 도메인 관련 API")
interface DiaryApiDocument {

    fun getDiaries(
        request: QueryDiariesRequest
    ): ResponseEntity<ApiResult<List<QueryDiariesResponse>>>

    fun getDiaryDetail(
        diaryId: UUID
    ): ResponseEntity<ApiResult<QueryDiaryDetailResponse>>
}
