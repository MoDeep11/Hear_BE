package modeep.hear.global.common.mapper

import modeep.hear.domain.common.vo.BaseTime
import modeep.hear.global.common.entity.JpaBaseTime
import org.springframework.stereotype.Component

@Component
class BaseTimeMapper {
    fun toModel(jpaBaseTime: JpaBaseTime): BaseTime {
        return BaseTime(
            createdAt = jpaBaseTime.createdAt,
            updatedAt = jpaBaseTime.updatedAt
        )
    }

    fun toEntity(baseTime: BaseTime): JpaBaseTime {
        return JpaBaseTime(
            createdAt = baseTime.createdAt,
            updatedAt = baseTime.updatedAt
        )
    }
}
