package modeep.hear.domain.user.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.user.port.`in`.DeleteUserUseCase
import modeep.hear.domain.user.port.out.CommandUserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DeleteUserService(
    private val commandUserPort: CommandUserPort,
    private val securityPort: SecurityPort
) : DeleteUserUseCase {
    override fun execute() {
        val user = securityPort.getCurrentUser()
        commandUserPort.delete(user.id)
    }
}
