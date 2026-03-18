package modeep.hear.domain.diary.service

import modeep.hear.domain.diary.exception.DiaryErrorCode
import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.port.`in`.QueryDiaryDetailUseCase
import modeep.hear.domain.diary.port.out.QueryDiaryPort
import modeep.hear.global.error.exception.BusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class QueryDiaryDetailService(
    private val queryDiaryPort: QueryDiaryPort
) : QueryDiaryDetailUseCase {
    override fun execute(diaryId: UUID): Diary {
        val diary = queryDiaryPort.findById(diaryId)
            ?: throw BusinessException(DiaryErrorCode.DIARY_NOT_FOUND)

        return diary
    }
}
