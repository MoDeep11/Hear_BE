package modeep.hear.infrastructure.adapter.out.user.persistence

import modeep.hear.domain.user.model.User
import modeep.hear.domain.user.port.out.UserPort
import modeep.hear.infrastructure.adapter.out.user.persistence.mapper.UserMapper
import modeep.hear.infrastructure.adapter.out.user.persistence.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserPersistenceAdapter(
    private val repository: UserRepository,
    private val mapper: UserMapper
) : UserPort {
    // --Query--//
    override fun findByEmail(email: String): User? {
        return repository.findByEmail(email)?.let { mapper.toModel(it) }
    }

    override fun existsByEmail(email: String): Boolean =
        repository.existsByEmail(email)

    override fun findById(id: UUID): User? {
        return repository.findByIdOrNull(id)?.let { mapper.toModel(it) }
    }

    // --Command--//
    override fun save(user: User) {
        val isExist = repository.existsById(user.id)
        val entity = mapper.toEntity(user, isNew = !isExist)
        repository.save(entity)
    }

    override fun delete(userId: UUID) {
        repository.deleteIfExists(userId)
    }
}
