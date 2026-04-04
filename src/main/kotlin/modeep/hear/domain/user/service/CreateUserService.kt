package modeep.hear.domain.user.service

import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.model.User
import modeep.hear.domain.user.model.UserCalendar
import modeep.hear.domain.user.model.UserProfile
import modeep.hear.domain.user.model.UserStat
import modeep.hear.domain.user.model.id.UserCalendarId
import modeep.hear.domain.user.port.`in`.CreateUserUseCase
import modeep.hear.domain.user.port.out.UserCalendarPort
import modeep.hear.domain.user.port.out.UserPort
import modeep.hear.domain.user.port.out.UserProfilePort
import modeep.hear.domain.user.port.out.UserStatPort
import modeep.hear.global.error.exception.BusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.YearMonth

@Service
@Transactional
class CreateUserService(
    private val userPort: UserPort,
    private val userStatPort: UserStatPort,
    private val userProfilePort: UserProfilePort,
    private val userCalendarPort: UserCalendarPort
) : CreateUserUseCase {
    override fun execute(user: User) {
        if (userPort.existsByEmail(user.email)) {
            throw BusinessException(UserErrorCode.EMAIL_ALREADY_EXISTS)
        }

        val userStat = UserStat.create(user.id)
        val userProfile = UserProfile.create(user.id, null)

        userPort.save(user)
        userStatPort.save(userStat)
        userProfilePort.save(userProfile)

        val yearMonth = YearMonth.now()
        val userCalendars = (1..yearMonth.lengthOfMonth()).map { day ->
            UserCalendar(
                id = UserCalendarId(
                    calendarDate = yearMonth.atDay(day),
                    userId = user.id
                )
            )
        }
        userCalendarPort.saveAll(userCalendars)
    }
}
