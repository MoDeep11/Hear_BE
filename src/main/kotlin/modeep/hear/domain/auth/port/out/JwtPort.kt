package modeep.hear.domain.auth.port.out

import jakarta.servlet.http.HttpServletRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.response.TokenResponse
import org.springframework.security.core.Authentication
import java.util.UUID

interface JwtPort {
    fun createToken(userId: UUID): TokenResponse

    fun getAuthentication(token: String): Authentication

    fun resolveToken(request: HttpServletRequest): String?
}
