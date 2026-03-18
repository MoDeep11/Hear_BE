package modeep.hear.domain.auth.service

import modeep.hear.domain.auth.port.`in`.LogoutAuthUseCase
import modeep.hear.domain.auth.port.out.JwtPort
import modeep.hear.domain.auth.port.out.RefreshTokenPort
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.LogoutRequest
import org.springframework.stereotype.Service

@Service
class LogoutAuthService(
    private val refreshTokenPort: RefreshTokenPort,
    private val jwtPort: JwtPort
) : LogoutAuthUseCase {
    override fun execute(request: LogoutRequest, accessToken: String) {
        refreshTokenPort.delete(request.refreshToken)

        val remainingTime = jwtPort.getRemainingTime(accessToken)

        if (remainingTime > 0) {
            jwtPort.registerBlacklist(accessToken, remainingTime)
        }
    }
}
