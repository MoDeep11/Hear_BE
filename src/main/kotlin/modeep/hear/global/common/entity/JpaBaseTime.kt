package modeep.hear.global.common.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Embeddable
class JpaBaseTime(
    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
)
