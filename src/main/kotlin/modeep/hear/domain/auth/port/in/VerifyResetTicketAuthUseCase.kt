package modeep.hear.domain.auth.port.`in`

interface VerifyResetTicketAuthUseCase {
    fun execute(ticket: String)
}
