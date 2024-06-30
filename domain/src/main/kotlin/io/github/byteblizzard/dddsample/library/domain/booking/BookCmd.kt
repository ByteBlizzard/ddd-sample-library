package io.github.byteblizzard.dddsample.library.domain.booking

import io.github.byteblizzard.dddsample.library.domain.user.UserId
import org.springframework.stereotype.Component

class ReserveCmd (
    val isbn: String,
    val reserveUser: UserId
)

@Component
class ReserveCmdHandler(
    private val reservationFactory: ReservationFactory,
    private val reservationRepository: ReservationRepository
) {
    fun handle(cmd: ReserveCmd) {
        val reservation = this.reservationFactory.create(cmd)
        this.reservationRepository.save(reservation)
    }
}