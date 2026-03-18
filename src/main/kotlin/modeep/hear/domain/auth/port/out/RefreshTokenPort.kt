package modeep.hear.domain.auth.port.out

import modeep.hear.domain.auth.model.RefreshToken

interface RefreshTokenPort {
    fun findByRefreshToken(refreshToken: String): RefreshToken?

    fun existsByRefreshToken(refreshToken: String): Boolean

    fun save(refreshToken: RefreshToken)

    fun delete(refreshToken: String)
}
