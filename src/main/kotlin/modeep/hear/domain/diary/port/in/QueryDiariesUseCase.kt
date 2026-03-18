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
