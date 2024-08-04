package io.github.byteblizzard.dddsample.library.domain.book

import io.github.byteblizzard.dddsample.library.domain.lend.BookReturnedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class PutOnShelfWhenReturnPolicy(
    private val putOnShelfCmdHandler: PutOnShelfCmdHandler
){
    @EventListener
    fun onEvent(event: BookReturnedEvent) {
        if (event.bookLostBeforeReturn) {
            putOnShelfCmdHandler.handle(PutOnShelfCmd(event.bookId))
        }
    }
}