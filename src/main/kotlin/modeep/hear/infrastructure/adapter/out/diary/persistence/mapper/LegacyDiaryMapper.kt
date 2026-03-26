package modeep.hear.infrastructure.adapter.out.diary.persistence.mapper

import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.model.DiaryImage
import modeep.hear.infrastructure.adapter.out.diary.persistence.entity.DiaryImageJpaEntity
import modeep.hear.infrastructure.adapter.out.diary.persistence.entity.DiaryJpaEntity
import org.mapstruct.Mapping

// MapStruct가 자동으로 구현
// @Mapper(
//    componentModel = "spring",
//    uses = [BaseTimeMapper::class]
// )
@Deprecated("Use DiaryMapper")
interface LegacyDiaryMapper {
    fun toModel(entity: DiaryJpaEntity): Diary

    @Mapping(target = "baseTime", ignore = true)
    fun toEntity(model: Diary): DiaryJpaEntity

    @Mapping(source = "diary.id", target = "diaryId")
    fun toImageModel(entity: DiaryImageJpaEntity): DiaryImage

    @Mapping(source = "diaryId", target = "diary.id")
    @Mapping(target = "baseTime", ignore = true)
    fun toImageEntity(model: DiaryImage): DiaryImageJpaEntity
}
