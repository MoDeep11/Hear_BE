package modeep.hear.infrastructure.adapter.out.user.mapper

import modeep.hear.domain.user.model.User
import modeep.hear.global.common.mapper.BaseTimeMapper
import modeep.hear.infrastructure.adapter.out.user.entity.UserJpaEntity
import org.springframework.stereotype.Component

@Component
class UserMapperImpl(
    private val baseTimeMapper: BaseTimeMapper
) : UserMapper {
    override fun toModel(entity: UserJpaEntity): User {
        return User(
            id = entity.id,
            email = entity.email,
            password = entity.password,
            role = entity.role,
            status = entity.status,
            baseTime = baseTimeMapper.toModel(entity.baseTime),
            isEmailSubscribed = entity.isEmailSubscribed // is 네이밍 때문에 직접 구현
        )
    }

    override fun toEntity(model: User): UserJpaEntity {
        return UserJpaEntity(
            email = model.email,
            password = model.password,
            role = model.role,
            status = model.status,
            isEmailSubscribed = model.isEmailSubscribed
        ).apply {
            this.baseTime = baseTimeMapper.toEntity(model.baseTime)
        }
    }
}
