package io.github.byteblizzard.dddsample.library.domain

import org.axonframework.modelling.command.AggregateLifecycle

interface 领域事件 {
}

fun applyEvent(event: Any) {
    AggregateLifecycle.apply(event)
}