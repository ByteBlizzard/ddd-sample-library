package io.github.byteblizzard.dddsample.library.domain.booking

import org.springframework.stereotype.Component

class CancelReservationCmd(
    val reservationId: ReservationId
)

@Component
class CancelReservationCmdHandler(
    val reservationRepository: ReservationRepository
) {
    fun handle(cmd: CancelReservationCmd) {
        val reservation = this.reservationRepository.findByIdOrError(cmd.reservationId)
        reservation.cancel(cmd)
        this.reservationRepository.save(reservation)
    }
}