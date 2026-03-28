package modeep.hear.domain.user.service

import modeep.hear.domain.auth.exception.AuthErrorCode
import modeep.hear.domain.auth.port.`in`.LogoutAuthUseCase
import modeep.hear.domain.auth.port.out.PasswordPort
import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.user.port.`in`.DeleteUserUseCase
import modeep.hear.domain.user.port.out.command.CommandUserPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.LogoutRequest
import modeep.hear.infrastructure.adapter.`in`.user.dto.request.DeleteUserRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DeleteUserService(
    private val commandUserPort: CommandUserPort,
    private val securityPort: SecurityPort,
    private val passwordPort: PasswordPort,
    private val logoutAuthUseCase: LogoutAuthUseCase,
) : DeleteUserUseCase {
    override fun execute(
        accessToken: String,
        request: DeleteUserRequest
    ) {
        val user = securityPort.getCurrentUser()

        if (!passwordPort.matches(request.password, user.getPassword())) {
            throw BusinessException(AuthErrorCode.PASSWORD_NOT_MATCH)
        }

        commandUserPort.delete(user.id)

        logoutAuthUseCase.execute(
            LogoutRequest(
                refreshToken = request.refreshToken
            ),
            accessToken = accessToken
        )
    }
}
