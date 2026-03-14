package modeep.hear.infrastructure.adapter.out.chat.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import modeep.hear.domain.chat.vo.AiImageTaskStatus
import modeep.hear.global.common.entity.BaseEntity
import java.util.UUID

@Entity
@Table(name = "ai_image_tasks")
class AiImageTaskJpaEntity(
    @Column(name = "session_id")
    val sessionId: UUID? = null,

    @Column(name = "diary_id")
    val diaryId: UUID? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 16)
    val status: AiImageTaskStatus = AiImageTaskStatus.RESERVED

) : BaseEntity()
