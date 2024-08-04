package io.github.byteblizzard.dddsample.library.domain.reservation

import io.github.byteblizzard.dddsample.library.domain.book.BookTakenOffShelfEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class CancelWhenTakeOffBookPolicy(
    private val cancelReservationCmdHandler: CancelReservationCmdHandler,
    private val reservationRepository: ReservationRepository
) {
    @EventListener
    fun onEvent(event: BookTakenOffShelfEvent) {
        val reservation = reservationRepository.findEffectiveByBookId(event.bookId) ?: return
        cancelReservationCmdHandler.handle(CancelReservationCmd(reservation.id))
    }
}