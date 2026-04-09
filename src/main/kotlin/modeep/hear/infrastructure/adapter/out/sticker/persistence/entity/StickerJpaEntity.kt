package modeep.hear.infrastructure.adapter.out.sticker.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import modeep.hear.domain.sticker.vo.StickerStatus
import modeep.hear.global.common.entity.BaseEntity
import java.util.UUID

@Entity
@Table(name = "stickers")
class StickerJpaEntity(
    @Column(name = "user_id", nullable = false)
    val userId: UUID,

    @Column(name = "diary_id")
    val diaryId: UUID? = null,

    @Column(name = "status", nullable = false, length = 16)
    @Enumerated(EnumType.STRING)
    val status: StickerStatus = StickerStatus.PENDING,

    @Column(name = "image_url", nullable = false, length = 512)
    val imageUrl: String,

    @Column(name = "keyword")
    val keyword: String? = null,

    id: UUID
) : BaseEntity(id)
