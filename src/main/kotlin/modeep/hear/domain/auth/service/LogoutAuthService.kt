package modeep.hear.domain.auth.service

import modeep.hear.domain.auth.port.`in`.LogoutAuthUseCase
import modeep.hear.domain.auth.port.out.JwtPort
import modeep.hear.domain.auth.port.out.RefreshTokenPort
import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.LogoutRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class LogoutAuthService(
    private val refreshTokenPort: RefreshTokenPort,
    private val jwtPort: JwtPort,
    private val securityPort: SecurityPort
) : LogoutAuthUseCase {
    override fun execute(request: LogoutRequest, accessToken: String) {
        val user = securityPort.getCurrentUser()
        refreshTokenPort.delete(request.refreshToken)

        val remainingTime = jwtPort.getRemainingTime(accessToken)

        if (remainingTime > 0) {
            jwtPort.registerBlacklist(accessToken, user.id, remainingTime)
        }
    }
}
