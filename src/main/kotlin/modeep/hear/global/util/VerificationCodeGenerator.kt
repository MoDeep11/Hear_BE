package modeep.hear.global.util

import org.springframework.stereotype.Component
import java.security.SecureRandom

@Component
class VerificationCodeGenerator {
    private val secureRandom = SecureRandom()

    fun generate(): String {
        val number = secureRandom.nextInt(1000000)
        return number.toString().padStart(6, '0')
    }
}
