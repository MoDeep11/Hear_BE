package modeep.hear.domain.storage.model

import modeep.hear.domain.common.annotation.Aggregate
import modeep.hear.domain.common.vo.BaseTime
import modeep.hear.domain.storage.vo.ServiceType
import java.time.LocalDateTime
import java.util.UUID

@Aggregate
data class PendingUpload(
    val id: UUID,
    val userId: UUID,
    val s3Key: String,
    val serviceType: ServiceType,
    val expiredAt: LocalDateTime,
    val baseTime: BaseTime
) {
    companion object {
        private const val EXPIRATION_MINUTES = 15L

        fun create(userId: UUID, s3Key: String, serviceType: ServiceType): PendingUpload {
            return PendingUpload(
                id = UUID.randomUUID(),
                userId = userId,
                s3Key = s3Key,
                serviceType = serviceType,
                expiredAt = LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES),
                baseTime = BaseTime()
            )
        }
    }
}
