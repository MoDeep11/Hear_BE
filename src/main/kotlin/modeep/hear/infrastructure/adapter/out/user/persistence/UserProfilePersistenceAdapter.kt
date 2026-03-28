package modeep.hear.infrastructure.adapter.out.user.persistence

import modeep.hear.domain.user.model.UserProfile
import modeep.hear.domain.user.port.out.UserProfilePort
import modeep.hear.global.common.mapper.BaseTimeMapper
import modeep.hear.infrastructure.adapter.out.user.entity.UserProfileJpaEntity
import modeep.hear.infrastructure.adapter.out.user.persistence.repository.UserProfileRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserProfilePersistenceAdapter(
    private val repo: UserProfileRepository,
    private val baseTimeMapper: BaseTimeMapper
) : UserProfilePort {
    override fun findByUserId(userId: UUID): UserProfile? {
        return repo.findByIdOrNull(userId)?.let {
            UserProfile(
                userId = it.userId,
                nickname = it.nickname,
                profileImageUrl = it.profileImageUrl,
                baseTime = baseTimeMapper.toModel(it.baseTime)
            )
        }
    }

    override fun save(userProfile: UserProfile) {
        repo.save(
            UserProfileJpaEntity(
                userId = userProfile.userId,
                nickname = userProfile.nickname,
                profileImageUrl = userProfile.profileImageUrl,
            )
        )
    }

    override fun delete(userId: UUID) {
        repo.deleteById(userId)
    }
}