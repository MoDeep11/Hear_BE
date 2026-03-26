package modeep.hear.domain.chat.port.out

import modeep.hear.domain.chat.port.out.command.CommandChatPort
import modeep.hear.domain.chat.port.out.query.QueryChatPort

interface ChatPort : QueryChatPort, CommandChatPort
