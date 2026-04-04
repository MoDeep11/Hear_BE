package modeep.hear.domain.auth.service

import modeep.hear.domain.auth.exception.AuthErrorCode
import modeep.hear.domain.auth.port.`in`.LogoutAuthUseCase
import modeep.hear.domain.auth.port.out.JwtPort
import modeep.hear.domain.auth.port.out.RefreshTokenPort
import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.LogoutRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LogoutAuthService(
    private val refreshTokenPort: RefreshTokenPort,
    private val jwtPort: JwtPort,
    private val securityPort: SecurityPort
) : LogoutAuthUseCase {
    override fun execute(request: LogoutRequest, accessToken: String) {
        val user = securityPort.getCurrentUser()

        val refreshToken = refreshTokenPort.findByRefreshToken(request.refreshToken)
            ?: throw BusinessException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND)

        if (refreshToken.userId != user.id) {
            throw BusinessException(AuthErrorCode.INVALID_TOKEN)
        }

        refreshTokenPort.delete(request.refreshToken)

        val remainingTime = jwtPort.getRemainingTime(accessToken)
        if (remainingTime > 0) {
            jwtPort.registerBlacklist(accessToken, user.id, remainingTime)
        }
    }
}
