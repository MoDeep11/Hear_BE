package modeep.hear.domain.diary.service

import modeep.hear.domain.diary.port.`in`.QueryDiariesUseCase
import modeep.hear.domain.diary.port.out.QueryDiaryPort
import modeep.hear.domain.diary.vo.DiarySourceType
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.QueryDiariesResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.YearMonth

@Service
@Transactional(readOnly = true)
class QueryDiariesService(
    private val queryDiaryPort: QueryDiaryPort
) : QueryDiariesUseCase {
    override fun execute(
        imageType: DiarySourceType,
        hasPhoto: Boolean,
        yearMonth: YearMonth,
        limit: Int,
        sort: String,
        tag: String?
    ): List<QueryDiariesResponse> {
        val sortParts = sort.split(",")
        val direction = if (sortParts.getOrElse(1) { "desc" } == "asc") Sort.Direction.ASC else Sort.Direction.DESC
        val pageable = PageRequest.of(0, limit, Sort.by(direction, "baseTime.createdAt"))

        val diaries = queryDiaryPort.findAllByMonthWithFilters(yearMonth, hasPhoto, imageType, pageable)

        return diaries
            .filter { diary -> tag == null || diary.tags?.contains(tag) == true }
            .map { diary ->
                QueryDiariesResponse(
                    id = diary.id!!,
                    thumbnailUrl = diary.diaryImages.minBy { it.order },
                    tags = diary.tags ?: emptyList(),
                    createdAt = diary.baseTime.createdAt.toLocalDate()
                )
            }
    }
}
