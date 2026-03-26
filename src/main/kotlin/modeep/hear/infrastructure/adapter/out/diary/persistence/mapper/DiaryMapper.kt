package modeep.hear.infrastructure.adapter.out.diary.persistence.mapper

import modeep.hear.domain.diary.model.Diary
import modeep.hear.infrastructure.adapter.out.diary.persistence.entity.DiaryJpaEntity

interface DiaryMapper {
    fun toModel(entity: DiaryJpaEntity): Diary

    fun toEntity(model: Diary): DiaryJpaEntity
}
