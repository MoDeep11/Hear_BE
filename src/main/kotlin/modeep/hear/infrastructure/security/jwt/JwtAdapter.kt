package modeep.hear.infrastructure.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import modeep.hear.domain.auth.exception.AuthErrorCode
import modeep.hear.domain.auth.model.BlacklistToken
import modeep.hear.domain.auth.model.RefreshToken
import modeep.hear.domain.auth.port.out.BlacklistTokenPort
import modeep.hear.domain.auth.port.out.JwtPort
import modeep.hear.domain.auth.port.out.RefreshTokenPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.auth.dto.response.TokenResponse
import modeep.hear.infrastructure.security.userdetails.CustomUserDetailsService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.time.LocalDateTime
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtAdapter(
    private val jwtProperties: JwtProperties,
    private val customUserDetailsService: CustomUserDetailsService,
    private val refreshTokenPort: RefreshTokenPort,
    private val blacklistTokenPort: BlacklistTokenPort
) : JwtPort {
    companion object {
        private const val PREFIX = "Bearer "
        private const val TYPE_CLAIM = "type"
        private const val ACCESS_TYPE = "access"
        private const val REFRESH_TYPE = "refresh"
        private const val MILLISECONDS = 1000
    }

    private val key: SecretKey = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray())

    override fun createToken(userId: String): TokenResponse {
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

    override fun resolveToken(bearerToken: String): String? {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(PREFIX, ignoreCase = true)) {
            val token = bearerToken.substring(PREFIX.length).trim()
            if (token.isNotEmpty()) return token
        }
        return null
    }

    override fun getRemainingTime(accessToken: String): Long {
        val expiration = getClaimsIgnoreExpiration(accessToken).expiration
        val remainTime = expiration.time - System.currentTimeMillis()
        return if (remainTime > 0) remainTime else 0L
    }

    override fun registerBlacklist(accessToken: String, remainingTime: Long) {
        blacklistTokenPort.save(
            BlacklistToken(
                accessToken = accessToken,
                timeToLive = remainingTime
            )
        )
    }

    override fun validateToken(token: String): Claims {
        return try {
            parseToken(token)
        } catch (e: ExpiredJwtException) {
            throw BusinessException(AuthErrorCode.EXPIRED_TOKEN)
        } catch (e: Exception) {
            throw BusinessException(AuthErrorCode.INVALID_TOKEN)
        }
    }

    private fun generateToken(userId: String, type: String, expirationSeconds: Long): String {
        val now = Date()

        return Jwts.builder()
            .subject(userId.toString())
            .claim(TYPE_CLAIM, type)
            .issuedAt(now)
            .expiration(Date(now.time + expirationSeconds * MILLISECONDS))
            .signWith(key)
            .compact()
    }

    private fun generateAccessToken(userId: String): String =
        generateToken(userId, ACCESS_TYPE, jwtProperties.accessExpiration)

    private fun generateRefreshToken(userId: String): String {
        val refreshToken = generateToken(userId, REFRESH_TYPE, jwtProperties.refreshExpiration)

        refreshTokenPort.save(
            RefreshToken(
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

    private fun parseToken(token: String): Claims {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token).payload
    }

    fun isBlacklist(token: String): Boolean =
        blacklistTokenPort.exists(token)
}
