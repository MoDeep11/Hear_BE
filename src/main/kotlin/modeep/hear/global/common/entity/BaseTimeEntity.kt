package modeep.hear.global.common.entity

import jakarta.persistence.Embedded
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity {
    @Embedded
    var baseTime: JpaBaseTime = JpaBaseTime()
}
