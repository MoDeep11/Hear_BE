package modeep.hear.domain.auth.port.out

import io.jsonwebtoken.Claims
import modeep.hear.infrastructure.adapter.`in`.auth.dto.response.TokenResponse
import org.springframework.security.core.Authentication

interface JwtPort {
    fun getAuthentication(token: String): Authentication

    fun resolveToken(bearerToken: String): String?

    fun createToken(userId: String): TokenResponse

    fun getRemainingTime(accessToken: String): Long

    fun registerBlacklist(accessToken: String, remainingTime: Long)

    fun validateToken(token: String): Claims
}
