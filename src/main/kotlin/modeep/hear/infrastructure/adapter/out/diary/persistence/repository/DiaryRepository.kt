package modeep.hear.infrastructure.adapter.out.diary.persistence.repository

import modeep.hear.infrastructure.adapter.out.diary.persistence.entity.DiaryJpaEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate
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
        WHERE d.user_id = :userId
        AND d.created_at >= :start AND d.created_at < :end
        AND (:hasPhoto = false OR EXISTS (
            SELECT 1 FROM diary_images di
            WHERE di.diary_id = d.id
            AND di.image_url IS NOT NULL
            AND (:imageType IS NULL OR di.source_type = :imageType)
        ))
        -- '?' 연산자 대신 jsonb_exists 함수 사용
        AND (:tag IS NULL OR jsonb_exists(d.tags, :tag))
        ORDER BY d.created_at DESC
    """,
        nativeQuery = true
    )
    fun findIdsByFilters(
        @Param("userId") userId: UUID,
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

    fun countByUserId(userId: UUID): Long

    @Query(
        """
        SELECT COUNT(DISTINCT d.id) FROM DiaryJpaEntity d
        JOIN d.diaryImages i
        WHERE d.userId = :userId
        AND d.baseTime.createdAt >= :start
        AND d.baseTime.createdAt < :end
        AND i.sourceType = 'AI_MADE'
        AND i.imageUrl IS NOT NULL
    """
    )
    fun countByUserIdAndCreatedAtBetweenWithAiImage(
        @Param("userId") userId: UUID,
        @Param("start") start: LocalDateTime,
        @Param("end") end: LocalDateTime
    ): Int

    fun findAllByUserIdAndBaseTimeCreatedAtGreaterThanEqualAndBaseTimeCreatedAtLessThan(
        userId: UUID,
        baseTimeCreatedAtAfter: LocalDateTime,
        baseTimeCreatedAtBefore: LocalDateTime
    ): List<DiaryJpaEntity>

    @Query(
        """
        SELECT DISTINCT CAST(d.baseTime.createdAt AS date) 
        FROM DiaryJpaEntity d 
        WHERE d.userId = :userId 
        ORDER BY CAST(d.baseTime.createdAt AS date) DESC
    """
    )
    fun findDistinctDatesByUserId(userId: UUID, pageable: Pageable): List<LocalDate>

    @Query(
        """
        SELECT COUNT(d) > 0 
        FROM DiaryJpaEntity d 
        WHERE d.userId = :userId 
        AND d.baseTime.createdAt BETWEEN :after AND :before
    """
    )
    fun existsByUserIdAndDateRange(
        userId: UUID,
        baseTimeCreatedAtAfter: LocalDateTime,
        baseTimeCreatedAtBefore: LocalDateTime
    ): Boolean

    @Query(
        """
        SELECT d FROM DiaryJpaEntity d 
        WHERE d.userId = :userId 
        AND d.baseTime.createdAt BETWEEN :after AND :before 
        ORDER BY d.baseTime.createdAt DESC 
        LIMIT 1
    """
    )
    fun findLatestDiary(
        userId: UUID,
        baseTimeCreatedAtAfter: LocalDateTime,
        baseTimeCreatedAtBefore: LocalDateTime
    ): DiaryJpaEntity?

    @Query(
        """
        SELECT d FROM DiaryJpaEntity d 
        WHERE d.baseTime.createdAt BETWEEN :after AND :before
    """
    )
    fun findAllByDateRange(
        baseTimeCreatedAtAfter: LocalDateTime,
        baseTimeCreatedAtBefore: LocalDateTime
    ): List<DiaryJpaEntity>

    @Query(
        value = """
        SELECT * FROM diaries
        WHERE user_id = :userId
        LIMIT 1 OFFSET :offset
    """,
        nativeQuery = true
    )
    fun findOneByUserIdWithOffset(@Param("userId") userId: UUID, @Param("offset") offset: Long): DiaryJpaEntity?
}
