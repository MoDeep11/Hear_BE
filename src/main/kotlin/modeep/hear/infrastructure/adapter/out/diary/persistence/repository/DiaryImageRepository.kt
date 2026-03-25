package modeep.hear.infrastructure.adapter.out.diary.persistence.repository

import modeep.hear.infrastructure.adapter.out.diary.entity.DiaryImageJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface DiaryImageRepository : JpaRepository<DiaryImageJpaEntity, UUID> {
    fun findBySessionId(sessionId: UUID): DiaryImageJpaEntity?
}