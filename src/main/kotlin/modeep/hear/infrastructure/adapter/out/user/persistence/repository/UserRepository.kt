package modeep.hear.infrastructure.adapter.out.user.persistence.repository

import modeep.hear.infrastructure.adapter.out.user.persistence.entity.UserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface UserRepository : JpaRepository<UserJpaEntity, UUID> {
    fun findByEmail(email: String): UserJpaEntity?

    fun existsByEmail(email: String): Boolean

    @Modifying
    @Query("DELETE FROM UserJpaEntity u WHERE u.id = :id")
    fun deleteIfExists(id: UUID)

    @Query("SELECT u.id FROM UserJpaEntity u")
    fun findAllIds(): List<UUID>
}
