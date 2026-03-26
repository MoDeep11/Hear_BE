package modeep.hear.domain.diary.port.out

import modeep.hear.domain.diary.port.out.command.CommandDiaryImagePort
import modeep.hear.domain.diary.port.out.query.QueryDiaryImagePort

interface DiaryImagePort : QueryDiaryImagePort, CommandDiaryImagePort
