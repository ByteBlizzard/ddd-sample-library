package io.github.byteblizzard.dddsample.library.domain.reservation

import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicLong

@Component
class ReservationIdGeneratorImpl: ReservationIdGenerator{
    private var counter = AtomicLong()

    override fun nextReservationId(): ReservationId {
        val count = counter.incrementAndGet()
        return ReservationId(value = "$count")
    }
}