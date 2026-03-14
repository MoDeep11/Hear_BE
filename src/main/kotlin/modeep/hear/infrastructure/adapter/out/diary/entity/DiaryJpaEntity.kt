package modeep.hear.infrastructure.adapter.out.diary.entity

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import modeep.hear.domain.common.vo.Emotion
import modeep.hear.domain.diary.vo.DiarySourceType
import modeep.hear.global.common.entity.BaseEntity
import modeep.hear.global.converter.TagsConverter
import java.util.UUID

@Entity
@Table(name = "diaries")
class DiaryJpaEntity(

    @Column(name = "user_id", nullable = false)
    val userId: UUID,

    @Column(name = "content", nullable = false, length = 1000)
    val content: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "emotion", nullable = false, length = 8)
    val emotion: Emotion,

    @Convert(converter = TagsConverter::class)
    @Column(name = "tags")
    val tags: List<String>? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type", nullable = false, length = 16)
    val sourceType: DiarySourceType,

    @Column(name = "session_id")
    val sessionId: UUID? = null
) : BaseEntity()
