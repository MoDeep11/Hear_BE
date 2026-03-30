package modeep.hear.domain.diary.port.out.external

import modeep.hear.infrastructure.adapter.out.diary.external.dto.request.GenerateDiaryImageRequest

interface FetchDiaryImagePort {
    suspend fun generateImage(req: GenerateDiaryImageRequest)
}
