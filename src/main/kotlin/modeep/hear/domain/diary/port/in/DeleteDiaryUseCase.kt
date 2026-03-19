package modeep.hear.domain.diary.port.`in`

import modeep.hear.domain.diary.model.Diary

interface DeleteDiaryUseCase {
    fun execute(
        diary: Diary,
    ): Diary
}
