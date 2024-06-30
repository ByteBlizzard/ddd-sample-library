package io.github.byteblizzard.dddsample.library.domain.user

import io.github.byteblizzard.dddsample.library.domain.DomainEvent
import io.github.byteblizzard.dddsample.library.domain.DomainEventPublisher
import io.github.byteblizzard.dddsample.library.domain.DomainRegistry
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class UserTest {
    @Test
    fun case1() {
        // given
        val eventPublisher: DomainEventPublisher = mockk()
        every { eventPublisher.publish(any(DomainEvent::class)) } just runs
        DomainRegistry.spring = mockk()
        every { DomainRegistry.spring.getBean(DomainEventPublisher::class.java) } returns eventPublisher

        val target = UserImpl(
            userId = UserId("one"),
            suspended = false,
            overdueTimes = 2
        )

        every { eventPublisher.publish(any(UserReturnBookOverdueEvent::class)) } just runs
        every { eventPublisher.publish(any(UserSuspendedEvent::class)) } just runs

        // when
        target.increaseOverdueTimes()

        // then
        assertEquals(3, target.overdueTimes)
        assertTrue(target.suspended)

        verifySequence {
            eventPublisher.publish(match {
                val event = it as? UserReturnBookOverdueEvent ?: return@match false
                event.userId.value == "one"
            })
            eventPublisher.publish(match {
                val event = it as? UserSuspendedEvent ?: return@match false
                event.userId.value == "one"
            })
        }

        confirmVerified(eventPublisher)
    }

    @Test
    fun enable() {
        // given
        val eventPublisher: DomainEventPublisher = mockk()
        every { eventPublisher.publish(any(DomainEvent::class)) } just runs
        DomainRegistry.spring = mockk()
        every { DomainRegistry.spring.getBean(DomainEventPublisher::class.java) } returns eventPublisher

        val target = UserImpl(
            userId = UserId("one"),
            suspended = true,
            overdueTimes = 3
        )
        every { eventPublisher.publish(any(UserEnabledEvent::class)) } just runs

        // when
        target.enable()

        // then
        assertEquals(0, target.overdueTimes)
        assertFalse(target.suspended)

        verifySequence {
            eventPublisher.publish(match {
                val event = it as? UserEnabledEvent ?: return@match false
                event.userId.value == "one"
            })
        }

        confirmVerified(eventPublisher)
    }
}