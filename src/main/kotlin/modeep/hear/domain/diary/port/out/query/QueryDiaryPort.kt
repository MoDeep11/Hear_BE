package modeep.hear.domain.diary.port.out.query

import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.vo.DiarySourceType
import org.springframework.data.domain.Pageable
import java.time.YearMonth
import java.util.UUID

interface QueryDiaryPort {
    fun findById(diaryId: UUID): Diary?

    fun findIdsByFilters(
        yearMonth: YearMonth,
        hasPhoto: Boolean,
        imageType: DiarySourceType,
        tag: String? = null,
        pageable: Pageable
    ): List<UUID>

    fun findAllByIdInWithImages(ids: List<UUID>): List<Diary>
}
