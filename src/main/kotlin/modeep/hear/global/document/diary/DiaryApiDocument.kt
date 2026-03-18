package modeep.hear.global.document.diary

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import modeep.hear.domain.diary.vo.DiarySourceType
import modeep.hear.global.common.response.ApiResult
import modeep.hear.global.document.annotation.ApiInternalServerErrorResponse
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.LoginRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.LogoutRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.RegisterRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.ReissueRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.ResetPasswordRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.SendEmailRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.VerifyEmailRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.response.TokenResponse
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.QueryDiariesResponse
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.QueryDiaryDetailResponse
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import java.time.YearMonth
import java.util.UUID

@Tag(name = "Diary", description = "Diary 도메인 관련 API")
interface DiaryApiDocument {

    fun getDiaries(
        imageType: DiarySourceType,
        hasPhoto: Boolean,
        yearMonth: YearMonth,
        limit: Int,
        sort: String,
        tag: String?
    ): ResponseEntity<ApiResult<QueryDiariesResponse>>

    fun getDiaryDetail(
        diaryId: UUID
    ): ResponseEntity<ApiResult<QueryDiaryDetailResponse>>
}
