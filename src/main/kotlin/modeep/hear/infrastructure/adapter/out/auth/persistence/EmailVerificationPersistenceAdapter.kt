package modeep.hear.infrastructure.adapter.out.auth.persistence

import modeep.hear.domain.auth.model.EmailVerification
import modeep.hear.domain.auth.port.out.EmailVerificationPort
import modeep.hear.infrastructure.adapter.out.auth.persistence.mapper.EmailVerificationMapper
import modeep.hear.infrastructure.adapter.out.auth.persistence.repository.EmailVerificationRepository
import org.springframework.stereotype.Component

@Component
class EmailVerificationPersistenceAdapter(
    private val emailVerificationRepository: EmailVerificationRepository,
    private val emailVerificationMapper: EmailVerificationMapper
) : EmailVerificationPort {
    override fun save(emailVerification: EmailVerification) {
        emailVerificationRepository.save(
            emailVerificationMapper.toEntity(emailVerification)
        )
    }
}
