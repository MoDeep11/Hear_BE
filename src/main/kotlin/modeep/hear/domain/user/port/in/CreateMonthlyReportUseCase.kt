package modeep.hear.domain.user.port.`in`

import java.util.UUID

interface CreateMonthlyReportUseCase {
    suspend fun execute(userId: UUID)
}
