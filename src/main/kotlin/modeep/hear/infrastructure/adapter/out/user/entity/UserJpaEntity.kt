package modeep.hear.infrastructure.adapter.out.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import modeep.hear.domain.user.vo.Role
import modeep.hear.domain.user.vo.UserStatus
import modeep.hear.global.common.entity.BaseEntity

@Entity
@Table(name = "users")
class UserJpaEntity(

    @Column(name = "email", nullable = false, unique = true, length = 100)
    val email: String,

    @Column(name = "password", nullable = false, length = 100)
    val password: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    val role: Role,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    val status: UserStatus = UserStatus.ACTIVE,

    @Column(name = "is_email_subscribed", nullable = false)
    val isEmailSubscribed: Boolean = false
) : BaseEntity()
