package modeep.hear.global.common.entity

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.UuidGenerator
import java.util.UUID

@MappedSuperclass
abstract class BaseUUIDEntity(
    id: UUID? = null
) {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false)
    var id: UUID? = id
        protected set // 외부 수정 방지
}
