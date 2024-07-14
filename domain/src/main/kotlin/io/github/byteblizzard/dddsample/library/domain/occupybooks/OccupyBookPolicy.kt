package io.github.byteblizzard.dddsample.library.domain.occupybooks

import io.github.byteblizzard.dddsample.library.domain.DomainEvent
import io.github.byteblizzard.dddsample.library.domain.reservation.BookReservedEvent
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
        }
    }
}