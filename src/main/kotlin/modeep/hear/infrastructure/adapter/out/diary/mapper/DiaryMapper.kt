package modeep.hear.infrastructure.adapter.out.diary.mapper

import modeep.hear.domain.diary.model.Diary
import modeep.hear.infrastructure.adapter.out.diary.entity.DiaryJpaEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface DiaryMapper {
    fun toModel(entity: DiaryJpaEntity): Diary

    fun toEntity(model: Diary): DiaryJpaEntity
}
