package io.github.byteblizzard.dddsample.library.domain.reservation

import org.springframework.stereotype.Component

class TryOverdueCmd(
    val id: ReservationId
)

@Component
class TryOverdueCmdHandler(
    private val reservationRepository: ReservationRepository
) {
    fun handle(cmd: TryOverdueCmd) {
        val reservation = this.reservationRepository.findByIdOrError(cmd.id)
        reservation.tryOverdue()
        this.reservationRepository.save(reservation)
    }
}