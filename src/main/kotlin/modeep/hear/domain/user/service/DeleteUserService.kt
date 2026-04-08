package modeep.hear.domain.user.service

import modeep.hear.domain.auth.port.`in`.LogoutAuthUseCase
import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.user.port.`in`.DeleteUserUseCase
import modeep.hear.domain.user.port.out.command.CommandUserPort
import modeep.hear.domain.user.vo.UserStatus
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.LogoutRequest
import modeep.hear.infrastructure.adapter.`in`.user.dto.request.DeleteUserRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DeleteUserService(
    private val commandUserPort: CommandUserPort,
    private val securityPort: SecurityPort,
    private val logoutAuthUseCase: LogoutAuthUseCase
) : DeleteUserUseCase {
    override fun execute(
        accessToken: String,
        request: DeleteUserRequest
    ) {
        val user = securityPort.getCurrentUser()

        user.status = UserStatus.DELETED
        commandUserPort.save(user)

        logoutAuthUseCase.execute(
            LogoutRequest(
                refreshToken = request.refreshToken
            ),
            rawAccessToken = accessToken
        )
    }
}
