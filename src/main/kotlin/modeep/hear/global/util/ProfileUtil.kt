package modeep.hear.global.util

import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
class ProfileUtil(private val env: Environment) {
    val isProd: Boolean get() = env.activeProfiles.contains("prod")
}
