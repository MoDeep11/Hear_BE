package modeep.hear.domain.diary.port.`in`

import modeep.hear.domain.diary.model.Diary

interface UpdateDiaryContentUseCase {
    fun execute(
        diary: Diary,
    ): Diary
}