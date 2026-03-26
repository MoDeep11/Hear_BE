package modeep.hear.domain.chat.port.out

import modeep.hear.domain.chat.port.out.command.CommandMessagePort
import modeep.hear.domain.chat.port.out.query.QueryMessagePort

interface MessagePort : QueryMessagePort, CommandMessagePort