package modeep.hear.domain.diary.port.out

import modeep.hear.domain.diary.port.out.command.CommandDiaryPort
import modeep.hear.domain.diary.port.out.external.FetchDiaryPort
import modeep.hear.domain.diary.port.out.query.QueryDiaryPort

interface DiaryPort : QueryDiaryPort, CommandDiaryPort, FetchDiaryPort
