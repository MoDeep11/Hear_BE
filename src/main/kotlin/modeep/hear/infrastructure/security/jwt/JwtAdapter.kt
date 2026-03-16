package modeep.hear.infrastructure.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import modeep.hear.domain.auth.exception.AuthErrorCode
import modeep.hear.domain.auth.port.`in`.TokenResponse
import modeep.hear.domain.auth.port.out.JwtPort
import modeep.hear.domain.auth.port.out.RefreshTokenPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.out.auth.persistence.entity.RefreshTokenRedisEntity
import modeep.hear.infrastructure.security.userdetails.CustomUserDetailsService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.time.LocalDateTime
import java.util.Date
import java.util.UUID
import javax.crypto.SecretKey

@Component
class JwtAdapter(
    private val jwtProperties: JwtProperties,
    private val customUserDetailsService: CustomUserDetailsService,
    private val refreshTokenPort: RefreshTokenPort
) : JwtPort {
    companion object {
        private const val PREFIX = "Bearer "
        private const val HEADER = "Authorization"
        private const val TYPE_CLAIM = "type"
        private const val ACCESS_TYPE = "access"
        private const val REFRESH_TYPE = "refresh"
        private const val MILLISECONDS = 1000
    }

    private val key: SecretKey = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray())

    override fun createToken(userId: UUID): TokenResponse {
        val now = LocalDateTime.now()

        return TokenResponse(
            accessToken = generateAccessToken(userId),
            refreshToken = generateRefreshToken(userId),
            accessExpiredAt = now.plusSeconds(jwtProperties.accessExpiration),
            refreshExpiredAt = now.plusSeconds(jwtProperties.refreshExpiration)
        )
    }

    override fun getAuthentication(token: String): Authentication {
        val claims = validateToken(token)
        val tokenType = claims[TYPE_CLAIM]

        return when (tokenType) {
            ACCESS_TYPE -> {
                val userDetails: UserDetails =
                    customUserDetailsService.loadUserByUsername(claims.subject)
                UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
            }
            else -> {
                throw BusinessException(AuthErrorCode.INVALID_TOKEN)
            }
        }
    }

    override fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(HEADER) ?: return null

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(PREFIX, ignoreCase = true)) {
            val token = bearerToken.substring(PREFIX.length).trim()
            if (token.isNotEmpty()) return token
        }
        return null
    }

    private fun generateToken(userId: UUID, type: String, expirationSeconds: Long): String {
        val now = Date()

        return Jwts.builder()
            .subject(userId.toString())
            .claim(TYPE_CLAIM, type)
            .issuedAt(now)
            .expiration(Date(now.time + expirationSeconds * MILLISECONDS))
            .signWith(key)
            .compact()
    }

    private fun generateAccessToken(userId: UUID): String =
        generateToken(userId, ACCESS_TYPE, jwtProperties.accessExpiration)

    private fun generateRefreshToken(userId: UUID): String {
        val refreshToken = generateToken(userId, REFRESH_TYPE, jwtProperties.refreshExpiration)

        refreshTokenPort.save(
            RefreshTokenRedisEntity(
                userId = userId,
                refreshToken = refreshToken,
                timeToLive = jwtProperties.refreshExpiration
            )
        )
        return refreshToken
    }

    // 만료된 토큰에서 claims를 가져옴
    fun getClaimsIgnoreExpiration(token: String): Claims {
        return try {
            parseToken(token)
        } catch (e: ExpiredJwtException) {
            e.claims
        } catch (e: Exception) {
            throw BusinessException(AuthErrorCode.INVALID_TOKEN)
        }
    }

    fun validateToken(token: String): Claims {
        return try {
            parseToken(token)
        } catch (e: ExpiredJwtException) {
            throw BusinessException(AuthErrorCode.EXPIRED_TOKEN)
        } catch (e: Exception) {
            throw BusinessException(AuthErrorCode.INVALID_TOKEN)
        }
    }

    private fun parseToken(token: String): Claims {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token).payload
    }
}
