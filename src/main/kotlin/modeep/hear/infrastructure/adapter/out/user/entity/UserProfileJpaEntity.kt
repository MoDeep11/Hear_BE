package modeep.hear.infrastructure.adapter.out.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import modeep.hear.global.common.entity.vo.JpaAuditTime
import java.util.UUID

@Entity
@Table(name = "user_profiles")
class UserProfileJpaEntity(
    @Id
    @Column(name = "user_id")
    val userId: UUID? = null,

    @Column(name = "nickname", nullable = false)
    val nickname: String,

    @Column(name = "profile_image_url")
    val profileImageUrl: String
) {
    @Embedded
    val auditTime: JpaAuditTime = JpaAuditTime()
}
