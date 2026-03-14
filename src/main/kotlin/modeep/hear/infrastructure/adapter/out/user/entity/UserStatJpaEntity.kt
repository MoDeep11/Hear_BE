package modeep.hear.infrastructure.adapter.out.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.MapsId
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import modeep.hear.global.common.entity.vo.JpaAuditTime
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "user_stats")
class UserStatJpaEntity(
    @Id
    @Column(name = "user_id")
    val userId: UUID? = null,

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: UserJpaEntity,

    @Column(name = "current_streak")
    var currentStreak: Int = 0,

    @Column(name = "total_diaries", nullable = false)
    var totalDiaries: Int = 0,

    @Column(name = "max_streak", nullable = false)
    var maxStreak: Int = 0,

    @Column(name = "last_written_at")
    var lastWrittenAt: LocalDateTime? = null
) {
    @Embedded
    val auditTime: JpaAuditTime = JpaAuditTime()
}
