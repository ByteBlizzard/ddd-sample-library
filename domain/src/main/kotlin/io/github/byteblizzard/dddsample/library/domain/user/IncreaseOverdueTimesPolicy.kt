package io.github.byteblizzard.dddsample.library.domain.user

import io.github.byteblizzard.dddsample.library.domain.lend.ReturnBookOverdueEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class IncreaseOverdueTimesPolicy(
    private val increaseOverdueTimesCmdHandler: IncreaseOverdueTimesCmdHandler
) {

    @EventListener
    fun onEvent(event: ReturnBookOverdueEvent) {
        increaseOverdueTimesCmdHandler.handle(IncreaseOverdueTimesCmd(userId = event.lendOutUser))
    }
}