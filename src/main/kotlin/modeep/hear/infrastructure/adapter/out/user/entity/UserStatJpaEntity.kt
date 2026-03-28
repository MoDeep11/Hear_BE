package modeep.hear.infrastructure.adapter.out.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import modeep.hear.global.common.entity.BaseTimeEntity
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "user_stats")
class UserStatJpaEntity(
    @Id
    @Column(name = "user_id")
    val userId: UUID,

    @Column(name = "current_streak")
    var currentStreak: Int = 0,

    @Column(name = "total_diaries", nullable = false)
    var totalDiaries: Int = 0,

    @Column(name = "max_streak", nullable = false)
    var maxStreak: Int = 0,

    @Column(name = "last_written_at")
    var lastWrittenAt: LocalDateTime? = null
) : BaseTimeEntity()
