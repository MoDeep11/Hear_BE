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
        value = """
        SELECT DISTINCT d.* FROM diaries d
        LEFT JOIN diary_images di ON d.id = di.diary_id
        WHERE d.created_at >= :start AND d.created_at < :end
        AND (:imageType IS NULL OR d.source_type = :imageType)
        AND (:hasPhoto = false OR EXISTS (
            SELECT 1 FROM diary_images
            WHERE diary_id = d.id
            AND image_url IS NOT NULL
        ))
        -- JSONB 태그 필터링 (단일 태그 존재 여부 확인)
        AND (:tag IS NULL OR d.tags ? :tag)
    """,
        nativeQuery = true
    )
    fun findAllByMonthWithFilters(
        @Param("start") start: LocalDateTime,
        @Param("end") end: LocalDateTime,
        @Param("imageType") imageType: DiarySourceType?,
        @Param("hasPhoto") hasPhoto: Boolean,
        @Param("tag") tag: String?,
        pageable: Pageable
    ): List<DiaryJpaEntity>
}
