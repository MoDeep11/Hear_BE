package modeep.hear.infrastructure.security

import modeep.hear.domain.auth.port.out.PasswordPort
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class SecurityAdapter(
    private val encoder: PasswordEncoder
) : PasswordPort {
    override fun matches(rawPassword: String, encodedPassword: String): Boolean =
        encoder.matches(rawPassword, encodedPassword)

    override fun encode(password: String): String {
        return encoder.encode(password)
    }
}
