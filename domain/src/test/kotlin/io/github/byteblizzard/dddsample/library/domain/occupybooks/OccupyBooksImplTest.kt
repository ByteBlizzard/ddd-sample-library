package io.github.byteblizzard.dddsample.library.domain.occupybooks

import io.github.byteblizzard.dddsample.library.domain.DomainEvent
import io.github.byteblizzard.dddsample.library.domain.DomainEventPublisher
import io.github.byteblizzard.dddsample.library.domain.DomainRegistry
import io.github.byteblizzard.dddsample.library.domain.user.UserId
import io.mockk.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class OccupyBooksImplTest {
    @Test
    fun case1() {
        // given
        val eventPublisher: DomainEventPublisher = mockk()
        every { eventPublisher.publish(any(DomainEvent::class)) } just runs
        DomainRegistry.spring = mockk()
        every { DomainRegistry.spring.getBean(DomainEventPublisher::class.java) } returns eventPublisher

        val target = OccupyBooksImpl(UserId("id"), 3)

        // when
        target.modify( -4)

        // then
        assertEquals(0, target.occupyCount)
        verify { eventPublisher.publish(match { it is OccupyBooksChangedEvent && it.newOccupyCount == 0 && it.oldOccupyCount == 3 && it.userId.value == "id" }) }
    }
}