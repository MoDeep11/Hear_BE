package modeep.hear.infrastructure.adapter.out.diary.persistence.repository

import modeep.hear.domain.diary.vo.DiarySourceType
import modeep.hear.infrastructure.adapter.out.diary.entity.DiaryJpaEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime
import java.util.UUID

interface DiaryRepository : JpaRepository<DiaryJpaEntity, UUID> {
    @Query(
        """
        select d from DiaryJpaEntity d 
        join fetch d.diaryImages 
        where d.id = :id
    """
    )
    fun findByIdWithImages(id: UUID): DiaryJpaEntity?

    @Query(
        """
        SELECT DISTINCT d FROM DiaryJpaEntity d 
        LEFT JOIN FETCH d.diaryImages 
        LEFT JOIN FETCH d.tags 
        WHERE d.baseTime.createdAt >= :start AND d.baseTime.createdAt < :end
        AND (:imageType IS NULL OR d.sourceType = :imageType)
        AND (:hasPhoto = false OR SIZE(d.diaryImages) > 0)
    """
    )
    fun findAllByMonthWithFilters(
        @Param("start") start: LocalDateTime,
        @Param("end") end: LocalDateTime,
        @Param("imageType") imageType: DiarySourceType?,
        @Param("hasPhoto") hasPhoto: Boolean,
        pageable: Pageable
    ): List<DiaryJpaEntity>
}
