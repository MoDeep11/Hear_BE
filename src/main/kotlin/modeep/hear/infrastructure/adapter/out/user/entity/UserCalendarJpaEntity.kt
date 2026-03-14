package modeep.hear.infrastructure.adapter.out.user.entity

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import modeep.hear.domain.common.vo.Emotion
import modeep.hear.global.common.entity.BaseTimeEntity
import modeep.hear.infrastructure.adapter.out.user.entity.id.UserCalendarIdEntity

@Entity
@Table(name = "user_calendars")
class UserCalendarJpaEntity(
    @EmbeddedId
    val id: UserCalendarIdEntity,

    @Column(name = "has_diary", nullable = false)
    val hasDiary: Boolean = false,

    @Enumerated(EnumType.STRING)
    @Column(name = "emotion", nullable = true, length = 8)
    val emotion: Emotion? = null
) : BaseTimeEntity()
