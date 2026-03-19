package modeep.hear.domain.diary.port.`in`

import modeep.hear.domain.diary.model.Diary

interface CreateDiaryUseCase {
    fun execute(
        diary: Diary,
    ): Diary
}
