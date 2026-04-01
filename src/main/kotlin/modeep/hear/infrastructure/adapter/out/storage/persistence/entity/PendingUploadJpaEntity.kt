package modeep.hear.infrastructure.adapter.out.storage.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import modeep.hear.domain.storage.vo.ServiceType
import modeep.hear.global.common.entity.BaseEntity
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "pending_uploads")
class PendingUploadJpaEntity(
    @Column(name = "user_id", nullable = false)
    val userId: UUID,

    @Column(name = "s3_key", nullable = false, length = 512)
    val s3Key: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type", nullable = false, length = 32)
    val serviceType: ServiceType,

    @Column(name = "expired_at", nullable = false)
    val expiredAt: LocalDateTime,

    id: UUID
) : BaseEntity(id)
