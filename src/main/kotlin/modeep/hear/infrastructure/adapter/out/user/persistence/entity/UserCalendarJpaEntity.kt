package modeep.hear.infrastructure.adapter.out.user.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId
import jakarta.persistence.Table
import modeep.hear.domain.common.vo.Emotion
import modeep.hear.global.common.entity.BaseTimeEntity
import modeep.hear.infrastructure.adapter.out.calendar.persistence.entity.CalendarJpaEntity
import modeep.hear.infrastructure.adapter.out.user.persistence.entity.id.UserCalendarIdEntity
import java.util.UUID

@Entity
@Table(name = "user_calendars")
class UserCalendarJpaEntity(
    @EmbeddedId
    val id: UserCalendarIdEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("calendarDate")
    @JoinColumn(name = "calendar_date")
    val calendar: CalendarJpaEntity,

    @Column(name = "has_diary", nullable = false)
    val hasDiary: Boolean = false,

    @Column(name = "diary_id", nullable = true)
    val diaryId: UUID? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "emotion", nullable = true, length = 8)
    val emotion: Emotion? = null
) : BaseTimeEntity()
