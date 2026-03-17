package modeep.hear.domain.auth.port.`in`

import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.ReissueRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.response.TokenResponse

interface ReissueAuthUseCase {
    fun execute(request: ReissueRequest, accessToken: String): TokenResponse
}
