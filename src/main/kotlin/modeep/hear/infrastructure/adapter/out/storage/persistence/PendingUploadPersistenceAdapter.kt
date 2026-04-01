package modeep.hear.infrastructure.adapter.out.storage.persistence

import modeep.hear.domain.storage.model.PendingUpload
import modeep.hear.domain.storage.port.out.PendingUploadPort
import modeep.hear.infrastructure.adapter.out.storage.persistence.entity.PendingUploadJpaEntity
import modeep.hear.infrastructure.adapter.out.storage.persistence.repository.PendingUploadRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class PendingUploadPersistenceAdapter(
    private val repo: PendingUploadRepository
) : PendingUploadPort {

    override fun save(pendingUpload: PendingUpload) {
        repo.save(toEntity(pendingUpload))
    }

    override fun deleteByS3Key(s3Key: String) {
        repo.deleteByS3Key(s3Key)
    }

    override fun findAllExpiredBefore(now: LocalDateTime): List<PendingUpload> {
        return repo.findAllByExpiredAtBefore(now).map { toModel(it) }
    }

    override fun deleteAll(pendingUploads: List<PendingUpload>) {
        repo.deleteAllById(pendingUploads.map { it.id })
    }

    private fun toEntity(model: PendingUpload) = PendingUploadJpaEntity(
        id = model.id,
        userId = model.userId,
        s3Key = model.s3Key,
        serviceType = model.serviceType,
        expiredAt = model.expiredAt
    )

    private fun toModel(entity: PendingUploadJpaEntity) = PendingUpload(
        id = entity.id,
        userId = entity.userId,
        s3Key = entity.s3Key,
        serviceType = entity.serviceType,
        expiredAt = entity.expiredAt,
        baseTime = modeep.hear.domain.common.vo.BaseTime(
            createdAt = entity.baseTime.createdAt,
            updatedAt = entity.baseTime.updatedAt
        )
    )
}
