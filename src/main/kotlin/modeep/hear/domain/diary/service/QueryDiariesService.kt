package modeep.hear.domain.diary.service

import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.port.`in`.QueryDiariesUseCase
import modeep.hear.domain.diary.port.out.QueryDiaryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class QueryDiariesService(
    private val queryDiaryPort: QueryDiaryPort
) : QueryDiariesUseCase {
    override fun execute(
        imageType: String,
        hasPhoto: Boolean,
        yearMonth: String,
        limit: Int,
        sort: String,
        tag: String?
    ): Diary {
        
    }
}
