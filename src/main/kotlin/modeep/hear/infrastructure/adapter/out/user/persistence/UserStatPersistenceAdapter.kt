package modeep.hear.infrastructure.adapter.out.user.persistence

import modeep.hear.domain.user.model.UserStat
import modeep.hear.domain.user.port.out.UserStatPort
import modeep.hear.global.common.mapper.BaseTimeMapper
import modeep.hear.infrastructure.adapter.out.user.persistence.entity.UserStatJpaEntity
import modeep.hear.infrastructure.adapter.out.user.persistence.repository.UserStatRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserStatPersistenceAdapter(
    private val repo: UserStatRepository,
    private val baseTimeMapper: BaseTimeMapper
) : UserStatPort {
    // -Query--//
    override fun findByUserId(userId: UUID): UserStat? {
        return repo.findByIdOrNull(userId)?.let {
            UserStat(
                userId = it.userId,
                currentStreak = it.currentStreak,
                totalDiaries = it.totalDiaries,
                maxStreak = it.maxStreak,
                lastWrittenAt = it.lastWrittenAt,
                baseTime = baseTimeMapper.toModel(it.baseTime)
            )
        }
    }

    //-Command--//
    override fun save(userStat: UserStat) {
        repo.save(
            UserStatJpaEntity(
                userId = userStat.userId,
                currentStreak = userStat.currentStreak,
                totalDiaries = userStat.totalDiaries,
                maxStreak = userStat.maxStreak,
                lastWrittenAt = userStat.lastWrittenAt,
            )
        )
    }

    override fun delete(userId: UUID) {
        repo.deleteById(userId)
    }
}