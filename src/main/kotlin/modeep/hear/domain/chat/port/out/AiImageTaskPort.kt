package modeep.hear.domain.chat.port.out

import modeep.hear.domain.chat.port.out.command.CommandAiImageTaskPort
import modeep.hear.domain.chat.port.out.query.QueryAiImageTaskPort

interface AiImageTaskPort : QueryAiImageTaskPort, CommandAiImageTaskPort
