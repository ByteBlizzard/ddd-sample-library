package io.github.byteblizzard.dddsample.library.domain

import org.springframework.stereotype.Component

interface DomainEventDispatcher {
    fun dispatchNow(临时事件队列: 临时事件队列)
}

@Component
class SyncDomainEventDispatcher(
    private val listeners: List<DomainEventListener>
) : DomainEventDispatcher {
    override fun dispatchNow(临时事件队列: 临时事件队列) {
        临时事件队列.获取事件列表().forEach { event ->
            listeners.forEach { listener ->
                listener.onEvent(event)
            }
        }
    }
}