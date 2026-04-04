package modeep.hear.domain.auth.port.out

import io.jsonwebtoken.Claims
import modeep.hear.infrastructure.adapter.`in`.auth.dto.response.TokenResponse
import org.springframework.security.core.Authentication
import java.util.UUID

interface JwtPort {
    fun getAuthentication(token: String): Authentication

    fun resolveToken(bearerToken: String): String?

    fun createToken(userId: UUID): TokenResponse

    fun getRemainingTime(accessToken: String): Long

    fun registerBlacklist(accessToken: String, userId: UUID, remainingTime: Long)

    fun validateToken(token: String): Claims
}
