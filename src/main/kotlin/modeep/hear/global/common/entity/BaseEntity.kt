package modeep.hear.global.common.entity

import jakarta.persistence.Embedded
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.UUID

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity(
    id: UUID
) : BaseUUIDEntity(id) {
    @Embedded
    var baseTime: JpaBaseTime = JpaBaseTime()
}
