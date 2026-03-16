package modeep.hear.infrastructure.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import modeep.hear.domain.auth.exception.AuthErrorCode
import modeep.hear.global.error.exception.BusinessException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtFilter(
    private val jwtAdapter: JwtAdapter
) : OncePerRequestFilter() {

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.requestURI
        val excludePath = listOf(
            "/api/v1/auth/login",
            "/api/v1/auth/reissue",
            "/api/v1/auth/register"
        )
        return excludePath.any { path.startsWith(it) }
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = jwtAdapter.resolveToken(request)

        if (token != null) {
            if (jwtAdapter.isBlacklist(token)) {
                throw BusinessException(AuthErrorCode.ALREADY_LOGOUT)
            }

            jwtAdapter.validateToken(token)
            val authentication = jwtAdapter.getAuthentication(token)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }
}
