package modeep.hear.domain.user.port.`in`

import modeep.hear.infrastructure.adapter.`in`.user.dto.response.UserStatisticsResponse

interface GetUserStatisticsUseCase {
    fun execute(): UserStatisticsResponse
}