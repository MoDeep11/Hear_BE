package modeep.hear.infrastructure.adapter.out.auth.persistence

import modeep.hear.domain.auth.model.EmailLimit
import modeep.hear.domain.auth.port.out.EmailLimitPort
import modeep.hear.infrastructure.adapter.out.auth.persistence.mapper.EmailLimitMapper
import modeep.hear.infrastructure.adapter.out.auth.persistence.repository.EmailLimitRepository
import org.springframework.stereotype.Component

@Component
class EmailLimitPersistenceAdapter(
    private val repo: EmailLimitRepository,
    private val mapper: EmailLimitMapper
) : EmailLimitPort {

    override fun save(emailLimit: EmailLimit) {
        repo.save(mapper.toEntity(emailLimit))
    }

    override fun findByEmail(email: String): EmailLimit? {
        return repo.findById(email).orElse(null)?.let { mapper.toModel(it) }
    }
}
