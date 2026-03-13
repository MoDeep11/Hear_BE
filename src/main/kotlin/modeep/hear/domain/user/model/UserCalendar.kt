package modeep.hear.domain.user.model

import modeep.hear.domain.common.annotation.Aggregate
import modeep.hear.domain.common.model.emotion.Emotion
import modeep.hear.domain.user.model.id.UserCalendarId

@Aggregate
data class UserCalendar(
    val id: UserCalendarId? = null,
    val hasDiary: Boolean = false,
    val emotion: Emotion? = null
)
