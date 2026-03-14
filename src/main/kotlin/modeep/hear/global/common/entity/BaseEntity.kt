package modeep.hear.global.common.entity

import jakarta.persistence.Embedded
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import modeep.hear.global.common.entity.vo.JpaAuditTime
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity : UUIDEntity() {
    @Embedded
    var jpaAuditTime: JpaAuditTime = JpaAuditTime()
}
