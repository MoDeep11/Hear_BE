package modeep.hear.infrastructure.adapter.out.diary

import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.model.DiaryAiComment
import modeep.hear.domain.diary.port.out.DiaryPort
import modeep.hear.domain.diary.vo.DiarySourceType
import modeep.hear.infrastructure.adapter.out.chat.external.dto.vo.History
import modeep.hear.infrastructure.adapter.out.chat.external.dto.vo.UserInfo
import modeep.hear.infrastructure.adapter.out.diary.external.DiaryExternalAdapter
import modeep.hear.infrastructure.adapter.out.diary.persistence.DiaryPersistenceAdapter
import org.springframework.context.annotation.Primary
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.util.UUID

@Primary
@Component
class DiaryCompositeAdapter(
    private val persistenceAdapter: DiaryPersistenceAdapter,
    private val externalAdapter: DiaryExternalAdapter
) : DiaryPort {
    // --Persistence--//
    override fun findById(diaryId: UUID): Diary? =
        persistenceAdapter.findById(diaryId)

    override fun findIdsByFilters(
        yearMonth: YearMonth,
        hasPhoto: Boolean,
        imageType: DiarySourceType,
        tag: String?,
        pageable: Pageable
    ): List<UUID> =
        persistenceAdapter.findIdsByFilters(yearMonth, hasPhoto, imageType, tag, pageable)

    override fun findAllByIdInWithImages(ids: List<UUID>): List<Diary> =
        persistenceAdapter.findAllByIdInWithImages(ids)

    override fun findDistinctDatesByUserId(
        userId: UUID,
        limit: Int
    ): List<LocalDate> =
        persistenceAdapter.findDistinctDatesByUserId(userId, limit)

    override fun existsByUserIdAndDate(userId: UUID, date: LocalDate): Boolean =
        persistenceAdapter.existsByUserIdAndDate(userId, date)

    override fun countByUserId(userId: UUID): Long =
        persistenceAdapter.countByUserId(userId)

    override fun findAllByUserIdAndYearMonth(
        userId: UUID,
        yearMonth: YearMonth
    ): List<Diary> =
        persistenceAdapter.findAllByUserIdAndYearMonth(userId, yearMonth)

    override fun countByUserIdAndYearMonthWithAiImage(
        userId: UUID,
        yearMonth: YearMonth
    ): Int =
        persistenceAdapter.countByUserIdAndYearMonthWithAiImage(userId, yearMonth)

    override fun findAllByCreatedAtBetween(
        start: LocalDateTime,
        end: LocalDateTime
    ): List<Diary> =
        persistenceAdapter.findAllByCreatedAtBetween(start, end)

    override fun findRandomByUserId(userId: UUID): Diary? =
        persistenceAdapter.findRandomByUserId(userId)

    override fun save(diary: Diary) =
        persistenceAdapter.save(diary)

    override fun deleteById(diaryId: UUID) =
        persistenceAdapter.deleteById(diaryId)

    // --External--//
    override suspend fun generateDiary(
        chatId: UUID,
        histories: List<History>,
        userInfo: UserInfo
    ) = externalAdapter.generateDiary(chatId, histories, userInfo)

    override suspend fun addComment(
        userInfo: UserInfo,
        diary: Diary
    ): DiaryAiComment =
        externalAdapter.addComment(userInfo, diary)
}
