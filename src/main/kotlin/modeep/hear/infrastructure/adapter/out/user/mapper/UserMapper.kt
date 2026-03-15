package modeep.hear.infrastructure.adapter.out.user.mapper

import modeep.hear.domain.user.model.User
import modeep.hear.global.common.mapper.BaseTimeMapper
import modeep.hear.infrastructure.adapter.out.user.entity.UserJpaEntity
import org.mapstruct.Mapper

@Mapper(
    componentModel = "spring",
    uses = [BaseTimeMapper::class]
)
interface UserMapper {
    fun toModel(entity: UserJpaEntity): User

    fun toEntity(model: User): UserJpaEntity
}
