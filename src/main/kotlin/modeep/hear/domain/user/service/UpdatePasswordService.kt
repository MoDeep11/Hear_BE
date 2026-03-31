package modeep.hear.domain.user.service

import modeep.hear.domain.auth.port.out.PasswordPort
import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.port.`in`.UpdatePasswordUseCase
import modeep.hear.domain.user.port.out.command.CommandUserPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.user.dto.request.UpdatePasswordRequest
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.UpdatePasswordResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class UpdatePasswordService(
    private val commandUserPort: CommandUserPort,
    private val securityPort: SecurityPort,
    private val passwordPort: PasswordPort
) : UpdatePasswordUseCase {
    override fun execute(request: UpdatePasswordRequest): UpdatePasswordResponse {
        if (request.newPassword != request.confirmPassword) {
            throw BusinessException(UserErrorCode.INVALID_VALUE, "새 비밀번호가 일치하지 않습니다.")
        }
        val user = securityPort.getCurrentUser()
        passwordPort.matches(request.oldPassword, user.getPassword())

        val updatedUser = user.updatePassword(passwordPort.encode(request.newPassword))
        val updatedAt = LocalDateTime.now()
        commandUserPort.save(updatedUser)

        // todo: 알림용 이메일 발송

        return UpdatePasswordResponse(
            emailSent = user.isEmailSubscribed,
            sentTo = user.email,
            updatedAt = updatedAt
        )
    }
}
