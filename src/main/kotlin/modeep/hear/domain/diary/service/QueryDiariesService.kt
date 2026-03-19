package modeep.hear.domain.diary.service

import modeep.hear.domain.diary.exception.DiaryErrorCode
import modeep.hear.domain.diary.port.`in`.QueryDiariesUseCase
import modeep.hear.domain.diary.port.out.QueryDiaryPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.diary.dto.request.QueryDiariesRequest
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.QueryDiariesResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class QueryDiariesService(
    private val queryDiaryPort: QueryDiaryPort
) : QueryDiariesUseCase {
    override fun execute(
        request: QueryDiariesRequest
    ): List<QueryDiariesResponse> {
        val sortParts = request.sort.split(",")
        val direction = if (sortParts.getOrElse(1) { "desc" }.equals("asc", ignoreCase = true)) {
            Sort.Direction.ASC
        } else {
            Sort.Direction.DESC
        }
        val pageable = PageRequest.of(0, request.limit, Sort.by(direction, "baseTime.createdAt"))

        val diaries = queryDiaryPort.findAllByMonthWithFilters(
            yearMonth = request.resolvedYearMonth,
            hasPhoto = request.hasPhoto,
            imageType = request.imageType,
            tag = request.tag,
            pageable = pageable
        )

        return diaries
            .map { diary ->
                QueryDiariesResponse(
                    id = diary.id ?: throw BusinessException(DiaryErrorCode.DIARY_NOT_FOUND),
                    thumbnailUrl = diary.diaryImages.minByOrNull { it.order }?.imageUrl
                        ?: throw BusinessException(DiaryErrorCode.DIARY_IMAGE_REQUIRED),
                    tags = diary.tags ?: emptyList(),
                    createdAt = diary.baseTime.createdAt.toLocalDate()
                )
            }
    }
}
