package io.github.byteblizzard.dddsample.library.domain.book

import io.github.byteblizzard.dddsample.library.domain.lend.BookLostEvent
import org.springframework.stereotype.Component

@Component
class TakeOffWhenLostPolicy(
    private val takeOffShelfCmdHandler: TakeOffShelfCmdHandler
) {
     fun onEvent(event: BookLostEvent) {
         takeOffShelfCmdHandler.handle(TakeOffShelfCmd(event.bookId))
     }
}