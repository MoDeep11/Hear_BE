package modeep.hear.infrastructure.adapter.out.diary.mapper

import modeep.hear.domain.diary.model.Diary
import modeep.hear.global.common.mapper.BaseTimeMapper
import modeep.hear.infrastructure.adapter.out.diary.entity.DiaryJpaEntity
import org.mapstruct.Mapper

// MapStruct가 자동으로 구현
@Mapper(
    componentModel = "spring",
    uses = [BaseTimeMapper::class]
)
interface DiaryMapper {
    fun toModel(entity: DiaryJpaEntity): Diary

    fun toEntity(model: Diary): DiaryJpaEntity
}
