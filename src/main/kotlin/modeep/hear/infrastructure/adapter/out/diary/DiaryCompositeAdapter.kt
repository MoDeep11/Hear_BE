package modeep.hear.infrastructure.adapter.out.diary

import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.port.out.DiaryPort
import modeep.hear.domain.diary.vo.DiarySourceType
import modeep.hear.infrastructure.adapter.out.diary.external.DiaryExternalAdapter
import modeep.hear.infrastructure.adapter.out.diary.persistence.DiaryPersistenceAdapter
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.YearMonth
import java.util.UUID

@Component
class DiaryCompositeAdapter(
    private val persistenceAdapter: DiaryPersistenceAdapter,
    private val externalAdapter: DiaryExternalAdapter
) : DiaryPort {
    //--Persistence--//
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

    override fun save(diary: Diary) =
        persistenceAdapter.save(diary)

    override fun deleteById(diaryId: UUID) =
        persistenceAdapter.deleteById(diaryId)
}