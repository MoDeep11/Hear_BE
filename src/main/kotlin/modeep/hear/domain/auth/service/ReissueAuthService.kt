package modeep.hear.domain.auth.service

import modeep.hear.domain.auth.exception.AuthErrorCode
import modeep.hear.domain.auth.port.`in`.ReissueAuthUseCase
import modeep.hear.domain.auth.port.out.JwtPort
import modeep.hear.domain.auth.port.out.RefreshTokenPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.ReissueRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.response.TokenResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class ReissueAuthService(
    private val jwtPort: JwtPort,
    private val refreshTokenPort: RefreshTokenPort
) : ReissueAuthUseCase {
    override fun execute(
        request: ReissueRequest,
        bearerAccessToken: String
    ): TokenResponse {
        val accessToken = jwtPort.resolveToken(bearerAccessToken)
            ?: throw BusinessException(AuthErrorCode.INVALID_TOKEN)
        val accessTokenClaim = jwtPort.validateToken(accessToken)

        val userId = accessTokenClaim.subject
        verifyRefreshTokenOwner(request.refreshToken, userId)

        val userUUID = runCatching { UUID.fromString(userId) }
            .getOrElse { throw BusinessException(AuthErrorCode.INVALID_TOKEN) }
        return jwtPort.createToken(userUUID)
    }

    private fun verifyRefreshTokenOwner(rawRefreshToken: String, userId: String) {
        if (!refreshTokenPort.existsByRefreshToken(rawRefreshToken)) {
            throw BusinessException(AuthErrorCode.INVALID_TOKEN)
        }

        val refreshTokenSubject = jwtPort.validateToken(rawRefreshToken).subject

        if (refreshTokenSubject != userId) {
            throw BusinessException(AuthErrorCode.INVALID_TOKEN)
        }
    }
}
