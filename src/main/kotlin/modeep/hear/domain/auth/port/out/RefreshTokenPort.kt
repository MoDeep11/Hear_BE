package modeep.hear.domain.auth.port.out

import modeep.hear.domain.auth.model.RefreshToken

interface RefreshTokenPort {
    fun save(refreshToken: RefreshToken)

    fun delete(refreshToken: String)
}
