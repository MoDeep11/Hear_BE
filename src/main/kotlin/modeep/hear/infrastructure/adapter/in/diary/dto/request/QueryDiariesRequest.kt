package modeep.hear.infrastructure.adapter.`in`.diary.dto.request

import modeep.hear.domain.diary.vo.DiarySourceType
import org.springframework.format.annotation.DateTimeFormat
import java.time.YearMonth

data class QueryDiariesRequest(
    val imageType: DiarySourceType? = DiarySourceType.MANUAL,
    val hasPhoto: Boolean = true,
    @field:DateTimeFormat(pattern = "yyyy-MM")
    val yearMonth: YearMonth? = null,
    val limit: Int = 32,
    val tag: String? = null
) {
    val resolvedYearMonth: YearMonth
        get() = yearMonth ?: YearMonth.now()
}
