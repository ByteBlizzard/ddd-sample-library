package io.github.byteblizzard.dddsample.library.domain

interface DomainEventListener {
    fun onEvent(event: 领域事件)
}