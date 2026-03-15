package modeep.hear.infrastructure.security.userdetails

import modeep.hear.domain.user.port.out.UserPort
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class CustomUserDetailsService(
    private val userPort: UserPort
) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userPort.findByEmail(email)
        return CustomUserDetails(user)
    }
}
