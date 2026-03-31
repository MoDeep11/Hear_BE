package modeep.hear.domain.diary.port.out.query

import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.vo.DiarySourceType
import org.springframework.data.domain.Pageable
import java.time.LocalDate
import java.time.LocalDateTime
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

    fun findDistinctDatesByUserId(userId: UUID, limit: Int): List<LocalDate>

    fun existsByUserIdAndDate(userId: UUID, date: LocalDate): Boolean

    fun countByUserId(userId: UUID): Long

    fun findAllByUserIdAndYearMonth(userId: UUID, yearMonth: YearMonth): List<Diary>

    fun countByUserIdAndYearMonthWithAiImage(userId: UUID, yearMonth: YearMonth): Int

    fun findAllByCreatedAtBetween(start: LocalDateTime, end: LocalDateTime): List<Diary>
}
