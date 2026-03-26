package modeep.hear.domain.diary.model

import modeep.hear.domain.common.annotation.Aggregate
import modeep.hear.domain.common.vo.BaseTime
import modeep.hear.domain.common.vo.Emotion
import modeep.hear.domain.diary.exception.DiaryErrorCode
import modeep.hear.domain.diary.vo.DiarySourceType
import modeep.hear.global.error.exception.BusinessException
import java.util.UUID
import kotlin.collections.forEach

@Aggregate
data class Diary(
    val id: UUID,
    val userId: UUID,
    var content: String,
    val emotion: Emotion,
    val tags: List<String>? = null,
    val baseTime: BaseTime,
    val sourceType: DiarySourceType = DiarySourceType.AI_MADE,
    val chatId: UUID? = null,
    val diaryImages: MutableList<DiaryImage> = mutableListOf()
) {
    companion object {
        fun create(
            userId: UUID,
            content: String,
            emotion: Emotion,
            tags: List<String>? = null,
            sourceType: DiarySourceType = DiarySourceType.MANUAL,
            chatId: UUID? = null,
            diaryImages: MutableList<DiaryImage> = mutableListOf()
        ): Diary {
            return Diary(
                id = UUID.randomUUID(),
                userId = userId,
                content = content,
                emotion = emotion,
                tags = tags,
                baseTime = BaseTime(),
                sourceType = sourceType,
                chatId = chatId,
                diaryImages = diaryImages
            )
        }
    }

    val images: List<DiaryImage>
        get() = diaryImages.toList()

    fun updateContent(content: String) {
        this.content = content
    }

    fun addImage(image: DiaryImage) {
        if (diaryImages.size >= 10) throw BusinessException(DiaryErrorCode.TOO_MANY_IMAGES)
        this.diaryImages.add(image)
        if (image.diaryId != this.id) {
            image.assignDiary(this)
        }
    }

    fun removeImage(image: DiaryImage) {
        diaryImages.remove(image)
    }

    fun updateImages(newImages: List<DiaryImage>) {
        val imagesToAdd = newImages.toList()
        if (imagesToAdd.size > 10) throw BusinessException(DiaryErrorCode.TOO_MANY_IMAGES)
        this.diaryImages.clear()
        imagesToAdd.forEach { this.addImage(it) }
    }

    fun validateOwner(currentUserId: UUID) {
        if (this.userId != currentUserId) {
            throw BusinessException(DiaryErrorCode.CANNOT_ACCESS_DIARY)
        }
    }
}
