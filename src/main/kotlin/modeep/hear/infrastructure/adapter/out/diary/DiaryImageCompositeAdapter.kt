package modeep.hear.infrastructure.adapter.out.diary

import modeep.hear.domain.diary.model.DiaryImage
import modeep.hear.domain.diary.port.out.DiaryImagePort
import modeep.hear.infrastructure.adapter.out.diary.external.DiaryImageExternalAdapter
import modeep.hear.infrastructure.adapter.out.diary.external.dto.request.GenerateDiaryImageRequest
import modeep.hear.infrastructure.adapter.out.diary.persistence.DiaryImagePersistenceAdapter
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.util.UUID

@Primary
@Component
class DiaryImageCompositeAdapter(
    private val persistenceAdapter: DiaryImagePersistenceAdapter,
    private val externalAdapter: DiaryImageExternalAdapter
) : DiaryImagePort {
    // --Persistence--//
    override fun findAllByChatId(chatId: UUID): List<DiaryImage> =
        persistenceAdapter.findAllByChatId(chatId)

    override fun saveAll(diaryImages: List<DiaryImage>) =
        persistenceAdapter.saveAll(diaryImages)

    override fun save(diaryImage: DiaryImage) =
        persistenceAdapter.save(diaryImage)

    override fun delete(diaryImageId: UUID) =
        persistenceAdapter.delete(diaryImageId)

    // --External--//
    override suspend fun generateImage(req: GenerateDiaryImageRequest) =
        externalAdapter.generateImage(req)
}
