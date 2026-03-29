package modeep.hear.domain.user.port.out

import modeep.hear.domain.user.port.out.command.CommandUserPort
import modeep.hear.domain.user.port.out.query.QueryUserPort

interface UserPort : CommandUserPort, QueryUserPort
