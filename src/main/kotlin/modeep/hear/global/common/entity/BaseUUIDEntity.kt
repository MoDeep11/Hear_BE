package modeep.hear.global.common.entity

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PostLoad
import jakarta.persistence.PostPersist
import org.hibernate.annotations.UuidGenerator
import org.springframework.data.domain.Persistable
import java.util.UUID

@MappedSuperclass
abstract class BaseUUIDEntity(
    id: UUID? = null
) : Persistable<UUID> {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false)
    var id: UUID? = id
        protected set // 외부 수정 방지

    override fun getId(): UUID? = id

    // --select 쿼리 문제 처리--//
    @Transient
    private var isNew = true

    override fun isNew(): Boolean = isNew

    @PostPersist
    @PostLoad
    protected fun markNotNew() {
        this.isNew = false
    }
}
