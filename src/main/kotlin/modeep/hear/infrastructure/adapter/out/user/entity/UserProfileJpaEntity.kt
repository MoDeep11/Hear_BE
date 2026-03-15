package modeep.hear.infrastructure.adapter.out.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import modeep.hear.global.common.entity.BaseTimeEntity
import java.util.UUID

@Entity
@Table(name = "user_profiles")
class UserProfileJpaEntity(
    @Id
    @Column(name = "user_id")
    val userId: UUID,

    @Column(name = "nickname", nullable = false, length = 20)
    val nickname: String,

    @Column(name = "profile_image_url", nullable = false)
    val profileImageUrl: String
) : BaseTimeEntity()
