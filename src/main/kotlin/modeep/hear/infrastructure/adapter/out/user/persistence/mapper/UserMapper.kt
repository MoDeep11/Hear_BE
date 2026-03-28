package modeep.hear.infrastructure.adapter.out.user.persistence.mapper

import modeep.hear.domain.user.model.User
import modeep.hear.infrastructure.adapter.out.user.persistence.entity.UserJpaEntity

// @Mapper(
//    componentModel = "spring",
//    uses = [BaseTimeMapper::class]
// )
interface UserMapper {
    fun toModel(entity: UserJpaEntity): User

    fun toEntity(model: User): UserJpaEntity
}
