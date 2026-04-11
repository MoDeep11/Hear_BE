package modeep.hear.infrastructure.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import modeep.hear.domain.auth.exception.AuthErrorCode
import modeep.hear.global.common.constant.SecurityConstants
import modeep.hear.global.error.exception.BusinessException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.AntPathMatcher
import org.springframework.web.filter.OncePerRequestFilter

class JwtFilter(
    private val jwtAdapter: JwtAdapter
) : OncePerRequestFilter() {

    companion object {
        private const val HEADER = "Authorization"
        private val pathMatcher = AntPathMatcher()
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.requestURI
        return SecurityConstants.PERMIT_PATHS.any { pattern -> pathMatcher.match(pattern, path) }
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // Early Return
        val bearerToken = request.getHeader(HEADER) ?: return filterChain.doFilter(request, response)
        val token = jwtAdapter.resolveToken(bearerToken) ?: return filterChain.doFilter(request, response)

        validateAccessToken(token)

        val authentication = jwtAdapter.getAuthentication(token)
        SecurityContextHolder.getContext().authentication = authentication

        filterChain.doFilter(request, response)
    }

    private fun validateAccessToken(token: String) {
        if (jwtAdapter.isBlacklist(token)) {
            throw BusinessException(AuthErrorCode.LOGOUT_TOKEN)
        }
        jwtAdapter.validateToken(token)
    }
}
