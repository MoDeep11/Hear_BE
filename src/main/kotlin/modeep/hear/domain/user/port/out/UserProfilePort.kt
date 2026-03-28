package modeep.hear.domain.user.port.out

import modeep.hear.domain.user.port.out.command.CommandUserProfilePort
import modeep.hear.domain.user.port.out.query.QueryUserProfilePort

interface UserProfilePort : QueryUserProfilePort, CommandUserProfilePort
