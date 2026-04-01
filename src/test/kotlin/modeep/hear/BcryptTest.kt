package modeep.hear

import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class BcryptTest {
    @Test
    fun dispenser() {
        val encoder = BCryptPasswordEncoder()
        println(encoder.encode("qwer"))
    }
}