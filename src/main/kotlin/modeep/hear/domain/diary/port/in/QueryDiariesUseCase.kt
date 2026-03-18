package modeep.hear.domain.diary.port.`in`

import modeep.hear.domain.diary.vo.DiarySourceType
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.QueryDiariesResponse
import java.time.YearMonth

interface QueryDiariesUseCase {
    fun execute(
        imageType: DiarySourceType,
        hasPhoto: Boolean,
        yearMonth: YearMonth,
        limit: Int,
        sort: String,
        tag: String?
    ): List<QueryDiariesResponse>
}
// `imageType` : MANUAL(default) / AI_MADE
//
// `hasPhoto` : ture(default) / false
// `yearMonth` : yyyy-mm, (default) 현재 연-월
//
// `limit`: (default) Int.MAX_VALUE
//
// `sort`: (default) createdAt,desc
//
// `tag`: (default) null, 태그 검색용

// @RequestParam @DateTimeFormat(pattern = "yyyy-MM") yearMonth: YearMonth,
//    @RequestParam(defaultValue = "10") limit: Long,
//    @RequestParam(defaultValue = "createdAt,desc") sort: String // "필드명,방향" 형식
