package modeep.hear.domain.user.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.user.port.`in`.UpdateEmailSubscriptionUseCase
import modeep.hear.domain.user.port.out.command.CommandUserPort
import modeep.hear.infrastructure.adapter.`in`.user.dto.request.UpdateEmailSubscriptionRequest
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.UpdateEmailSubscriptionResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class UpdateEmailSubscriptionService(
    private val commandUserPort: CommandUserPort,
    private val securityPort: SecurityPort
) : UpdateEmailSubscriptionUseCase {
    override fun execute(request: UpdateEmailSubscriptionRequest): UpdateEmailSubscriptionResponse {
        val user = securityPort.getCurrentUser()
        user.isEmailSubscribed = request.isSubscribed
        val updatedAt = LocalDateTime.now()
        commandUserPort.save(user)

        return UpdateEmailSubscriptionResponse(
            isSubscribed = user.isEmailSubscribed,
            updatedAt = updatedAt
        )
    }
}
