package io.github.byteblizzard.dddsample.library.domain.availablebooks

import io.github.byteblizzard.dddsample.library.domain.DomainEvent
import io.github.byteblizzard.dddsample.library.domain.DomainEventPublisher
import io.github.byteblizzard.dddsample.library.domain.DomainRegistry
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AvailableBooksTest {
    @Test
    fun case1() {
        // given
        val eventPublisher: DomainEventPublisher = mockk()
        every { eventPublisher.publish(any(DomainEvent::class)) } just runs
        DomainRegistry.spring = mockk()
        every { DomainRegistry.spring.getBean(DomainEventPublisher::class.java) } returns eventPublisher

        val target = AvailableBooksImpl(isbn = "isbn", bookIds = mutableSetOf())

        // then
        assertFalse(target.containsBook("书1"))

        target.add("书1")
        assertTrue(target.containsBook("书1"))

        target.add("书1")
        assertTrue(target.containsBook("书1"))

        target.remove("书1")
        assertFalse(target.containsBook("书1"))

        target.remove("书1")

        verify(exactly = 1) { eventPublisher.publish(match { it is BookAvailableEvent && it.bookId == "书1" && it.isbn == "isbn" }) }
        verify(exactly = 1) { eventPublisher.publish(match { it is BookUnavailableEvent && it.bookId == "书1" && it.isbn == "isbn" }) }
    }
}