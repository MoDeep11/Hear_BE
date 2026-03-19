package modeep.hear.domain.diary.port.`in`

import modeep.hear.infrastructure.adapter.`in`.diary.dto.request.QueryDiariesRequest
import modeep.hear.infrastructure.adapter.`in`.diary.dto.response.QueryDiariesResponse

interface QueryDiariesUseCase {
    fun execute(
        request: QueryDiariesRequest
    ): List<QueryDiariesResponse>
}
