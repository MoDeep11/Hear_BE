package modeep.hear.infrastructure.adapter.out.diary.persistence.entity.id

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class StickerPlacementJpaEntity(
    @Column(name = "position_x", nullable = false)
    val positionX: Double = 0.0,

    @Column(name = "position_y", nullable = false)
    val positionY: Double = 0.0,

    @Column(name = "rotation", nullable = false)
    val rotation: Double = 0.0,

    @Column(name = "scale", nullable = false)
    val scale: Double = 1.0
)
