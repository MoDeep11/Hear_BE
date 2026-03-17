package modeep.hear.domain.auth.port.out

interface PasswordPort {
    fun matches(rawPassword: String, encodedPassword: String): Boolean

    fun encode(password: String): String
}
