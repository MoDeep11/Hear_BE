package modeep.hear.infrastructure.adapter.out.user.persistence

import modeep.hear.domain.user.model.User
import modeep.hear.domain.user.port.out.UserPort
import modeep.hear.infrastructure.adapter.out.user.mapper.UserMapper
import modeep.hear.infrastructure.adapter.out.user.persistence.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class UserPersistenceAdapter(
    private val repository: UserRepository,
    private val mapper: UserMapper
) : UserPort {
    // --Query--//
    override fun findByEmail(email: String): User? {
        return repository.findByEmail(email) ?.let { mapper.toModel(it) }
    }

    override fun existsByEmail(email: String): Boolean =
        repository.existsByEmail(email)

    // --Command--//
    override fun save(user: User) {
        val userEntity = mapper.toEntity(user)
        repository.save(userEntity)
    }
}
