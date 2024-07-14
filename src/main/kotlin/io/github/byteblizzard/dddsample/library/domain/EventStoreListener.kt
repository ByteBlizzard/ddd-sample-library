package io.github.byteblizzard.dddsample.library.domain

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.byteblizzard.dddsample.library.db.EventRecord
import io.github.byteblizzard.dddsample.library.db.EventRecordDao
import io.github.byteblizzard.dddsample.library.domain.book.Book
import io.github.byteblizzard.dddsample.library.domain.book.BookStockedInEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class EventStoreListener(
    private val eventRecordDao: EventRecordDao,
    private val objectMapper: ObjectMapper
) {
    @EventListener
    fun onDomainEvent(domainEvent: DomainEvent) {
        when (domainEvent) {
            is BookStockedInEvent -> {
                eventRecordDao.save(EventRecord(
                    eventType = BookStockedInEvent::class.qualifiedName ?: throw IllegalStateException(),
                    aggregateType = Book::class.qualifiedName ?: throw IllegalStateException(),
                    aggregateId = domainEvent.bookId,
                    content = objectMapper.writeValueAsString(domainEvent)
                ))
            }
        }
    }
}