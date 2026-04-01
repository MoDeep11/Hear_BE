package modeep.hear.infrastructure.adapter.out.storage.persistence.repository

import modeep.hear.infrastructure.adapter.out.storage.persistence.entity.PendingUploadJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import java.util.UUID

interface PendingUploadRepository : JpaRepository<PendingUploadJpaEntity, UUID> {
    @Modifying
    @Query("DELETE FROM PendingUploadJpaEntity p WHERE p.s3Key = :s3Key")
    fun deleteByS3Key(s3Key: String)

    fun findAllByExpiredAtBefore(now: LocalDateTime): List<PendingUploadJpaEntity>
}
