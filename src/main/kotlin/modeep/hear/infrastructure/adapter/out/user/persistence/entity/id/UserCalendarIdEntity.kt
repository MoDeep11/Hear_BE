package modeep.hear.infrastructure.adapter.out.user.persistence.entity.id

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable
import java.time.LocalDate
import java.util.UUID

@Embeddable
data class UserCalendarIdEntity(
    @Column(name = "calendar_date")
    val calendarDate: LocalDate,

    @Column(name = "user_id")
    val userId: UUID
) : Serializable
