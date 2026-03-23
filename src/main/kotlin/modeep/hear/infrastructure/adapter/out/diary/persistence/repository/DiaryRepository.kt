package modeep.hear.infrastructure.adapter.out.diary.persistence.repository

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
        SELECT d.id FROM diaries d
        WHERE d.created_at >= :start AND d.created_at < :end
        AND (:imageType IS NULL OR d.source_type = :imageType)
        AND (:hasPhoto = false OR EXISTS (
            SELECT 1 FROM diary_images
            WHERE diary_id = d.id
            AND image_url IS NOT NULL
        ))
        -- '?' 연산자 대신 jsonb_exists 함수 사용
        AND (:tag IS NULL OR jsonb_exists(d.tags, :tag))
        ORDER BY d.created_at DESC
    """,
        nativeQuery = true
    )
    fun findIdsByFilters(
        @Param("start") start: LocalDateTime,
        @Param("end") end: LocalDateTime,
        @Param("imageType") imageType: String?,
        @Param("hasPhoto") hasPhoto: Boolean,
        @Param("tag") tag: String?,
        pageable: Pageable
    ): List<UUID>

    @Query(
        """
        SELECT DISTINCT d FROM DiaryJpaEntity d
        LEFT JOIN FETCH d.diaryImages
        WHERE d.id IN :ids
        ORDER BY d.baseTime.createdAt DESC
    """
    )
    fun findAllByIdInWithImages(@Param("ids") ids: List<UUID>): List<DiaryJpaEntity>
}
