package modeep.hear.infrastructure.adapter.out.auth.persistence

import modeep.hear.domain.auth.model.EmailVerification
import modeep.hear.domain.auth.port.out.EmailVerificationPort
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.out.auth.persistence.mapper.EmailVerificationMapper
import modeep.hear.infrastructure.adapter.out.auth.persistence.repository.EmailVerificationRepository
import org.springframework.stereotype.Component

@Component
class EmailVerificationPersistenceAdapter(
    private val emailVerificationRepository: EmailVerificationRepository,
    private val emailVerificationMapper: EmailVerificationMapper
) : EmailVerificationPort {

    // --Query--//
    override fun findByEmail(email: String): EmailVerification? {
        val entity = emailVerificationRepository.findByEmail(email)
            ?: throw BusinessException(UserErrorCode.EMAIL_NOT_FOUND)
        return emailVerificationMapper.toDomain(entity)
    }

    // --Command--//
    override fun save(emailVerification: EmailVerification) {
        emailVerificationRepository.save(
            emailVerificationMapper.toEntity(emailVerification)
        )
    }

    override fun delete(email: String) {
        emailVerificationRepository.deleteById(email)
    }
}
