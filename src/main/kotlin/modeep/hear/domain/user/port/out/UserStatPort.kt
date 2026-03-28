package modeep.hear.domain.user.port.out

import modeep.hear.domain.user.port.out.command.CommandUserStatPort
import modeep.hear.domain.user.port.out.query.QueryUserStatPort

interface UserStatPort : QueryUserStatPort, CommandUserStatPort
