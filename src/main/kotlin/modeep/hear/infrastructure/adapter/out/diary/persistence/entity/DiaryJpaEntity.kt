package modeep.hear.infrastructure.adapter.out.diary.persistence.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.OneToMany
import jakarta.persistence.OrderBy
import jakarta.persistence.Table
import modeep.hear.domain.common.vo.Emotion
import modeep.hear.domain.diary.vo.DiarySourceType
import modeep.hear.global.common.entity.BaseEntity
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
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

    @JdbcTypeCode(SqlTypes.JSON) // jsonb 타입
    @Column(name = "tags", columnDefinition = "jsonb")
    val tags: List<String>? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type", nullable = false, length = 16)
    val sourceType: DiarySourceType,

    @Column(name = "chat_id")
    val chatId: UUID? = null,

    @OneToMany(mappedBy = "diary", cascade = [CascadeType.ALL], orphanRemoval = true)
    @OrderBy("order ASC")
    val diaryImages: MutableList<DiaryImageJpaEntity> = mutableListOf(),

    id: UUID
) : BaseEntity(id) {

    fun addImage(
        diaryImage: DiaryImageJpaEntity
    ) {
        this.diaryImages.add(diaryImage)
        if (diaryImage.diary != this) {
            diaryImage.assignDiary(this)
        }
    }

    fun updateImages(newImages: List<DiaryImageJpaEntity>) {
        this.diaryImages.clear()
        newImages.forEach { this.addImage(it) }
    }
}
