package modeep.hear.domain.user.port.`in`

import modeep.hear.infrastructure.adapter.`in`.user.dto.response.UserSummaryResponse

interface GetSummaryUseCase {
    fun execute(): UserSummaryResponse
}