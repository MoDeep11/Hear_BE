package modeep.hear.domain.auth.port.out

interface EmailLimitPort {
    fun saveIfAbsent(email: String): Boolean
}
