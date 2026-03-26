package modeep.hear.infrastructure.adapter.out.diary.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import modeep.hear.domain.diary.vo.DiaryImageStatus
import modeep.hear.domain.diary.vo.DiarySourceType
import modeep.hear.global.common.entity.BaseEntity
import java.util.UUID

@Entity
@Table(name = "diary_images")
class DiaryImageJpaEntity(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    var diary: DiaryJpaEntity? = null,

    @Column(name = "image_url", length = 512)
    val imageUrl: String? = null,

    @Column(name = "display_order", nullable = false)
    val order: Int = 0,

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type", nullable = false, length = 16)
    val sourceType: DiarySourceType = DiarySourceType.AI_MADE,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 16)
    val diaryImageStatus: DiaryImageStatus = DiaryImageStatus.PROCESSING,

    @Column(name = "chat_id")
    val chatId: UUID? = null,

    id: UUID
) : BaseEntity(id) {

    fun assignDiary(diary: DiaryJpaEntity) {
        this.diary?.diaryImages?.remove(this)
        this.diary = diary
        if (!diary.diaryImages.contains(this)) {
            diary.addImage(this)
        }
    }
}
