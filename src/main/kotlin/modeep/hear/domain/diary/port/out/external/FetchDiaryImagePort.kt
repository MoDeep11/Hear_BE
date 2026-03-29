package modeep.hear.domain.diary.port.out.external

import modeep.hear.infrastructure.adapter.out.diary.external.dto.request.GenerateImageRequest

interface FetchDiaryImagePort {
    suspend fun generateImage(req: GenerateImageRequest)
}

