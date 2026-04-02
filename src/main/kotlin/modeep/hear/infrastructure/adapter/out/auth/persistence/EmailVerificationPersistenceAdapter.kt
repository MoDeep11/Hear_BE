package modeep.hear.infrastructure.adapter.out.auth.persistence

import modeep.hear.domain.auth.model.EmailVerification
import modeep.hear.domain.auth.port.out.EmailVerificationPort
import modeep.hear.infrastructure.adapter.out.auth.persistence.mapper.EmailVerificationMapper
import modeep.hear.infrastructure.adapter.out.auth.persistence.repository.EmailVerificationRepository
import org.springframework.stereotype.Component

@Component
class EmailVerificationPersistenceAdapter(
    private val repo: EmailVerificationRepository,
    private val mapper: EmailVerificationMapper
) : EmailVerificationPort {

    // --Query--//
    override fun findByEmail(email: String): EmailVerification? {
        return repo.findById(email).orElse(null)?.let { mapper.toModel(it) }
    }

    // --Command--//
    override fun save(emailVerification: EmailVerification) {
        repo.save(
            mapper.toEntity(emailVerification)
        )
    }

    override fun delete(email: String) {
        repo.deleteById(email)
    }
}
