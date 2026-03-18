package modeep.hear.infrastructure.adapter.out.diary.persistence.repository

import modeep.hear.infrastructure.adapter.out.diary.entity.DiaryJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime
import java.util.UUID

interface DiaryRepository : JpaRepository<DiaryJpaEntity, UUID> {
    @Query("""
        select d from DiaryJpaEntity d 
        join fetch d.diaryImages 
        where d.id = :id
    """)
    fun findByIdWithImages(id: UUID): DiaryJpaEntity?

    @Query("""
        SELECT DISTINCT d FROM DiaryJpaEntity d 
        LEFT JOIN FETCH d.diaryImages 
        WHERE d.baseTime.createdAt >= :startDateTime AND d.baseTime.createdAt < :endDateTime
    """)
    fun findAllByMonthWithImages(
        @Param("startDateTime") start: LocalDateTime,
        @Param("endDateTime") end: LocalDateTime
    ): List<DiaryJpaEntity>
}