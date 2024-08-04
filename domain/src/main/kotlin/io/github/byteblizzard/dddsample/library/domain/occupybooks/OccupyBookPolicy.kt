package io.github.byteblizzard.dddsample.library.domain.occupybooks

import io.github.byteblizzard.dddsample.library.domain.DomainEvent
import io.github.byteblizzard.dddsample.library.domain.lend.BookLentOutEvent
import io.github.byteblizzard.dddsample.library.domain.lend.BookLostEvent
import io.github.byteblizzard.dddsample.library.domain.lend.BookReturnedEvent
import io.github.byteblizzard.dddsample.library.domain.reservation.BookReservedEvent
import io.github.byteblizzard.dddsample.library.domain.reservation.ReservationCanceledEvent
import io.github.byteblizzard.dddsample.library.domain.reservation.ReservationOverdueEvent
import org.springframework.stereotype.Component

@Component
class OccupyBookPolicy (
    private val modifyUserOccupyBooksCmdHandler: ModifyUserOccupyBooksCmdHandler
){
    fun onDomainEvent(event: DomainEvent) {
        when (event) {
            is BookReservedEvent -> {
                modifyUserOccupyBooksCmdHandler.handle(ModifyUserOccupyBooksCmd(
                    event.reserveUserId,
                    1
                ))
            }
            is BookLentOutEvent -> {
                modifyUserOccupyBooksCmdHandler.handle(ModifyUserOccupyBooksCmd(
                    event.lendOutUserId,
                    1
                ))
            }
            is BookReturnedEvent -> {
                modifyUserOccupyBooksCmdHandler.handle(ModifyUserOccupyBooksCmd(
                    event.lendOutUser,
                    -1
                ))
            }
            is ReservationCanceledEvent -> {
                modifyUserOccupyBooksCmdHandler.handle(ModifyUserOccupyBooksCmd(
                    event.reserveUserId,
                    -1
                ))
            }
            is ReservationOverdueEvent -> {
                modifyUserOccupyBooksCmdHandler.handle(ModifyUserOccupyBooksCmd(
                    event.reserveUserId,
                    -1
                ))
            }
            is BookLostEvent -> {
                modifyUserOccupyBooksCmdHandler.handle(ModifyUserOccupyBooksCmd(
                    event.lendOutUser,
                    -1
                ))
            }
        }
    }
}