package modeep.hear.domain.auth.port.out

import modeep.hear.domain.auth.model.BlacklistToken

interface BlacklistTokenPort {
    fun save(token: BlacklistToken)

    fun exists(token: String): Boolean
}
