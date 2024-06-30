package io.github.byteblizzard.dddsample.library.domain

import io.github.byteblizzard.dddsample.library.domain.book.BookRepository
import io.github.byteblizzard.dddsample.library.domain.availablebooks.AvailableBooksRepository
import org.springframework.context.ApplicationContext

object DomainRegistry {
    lateinit var spring: ApplicationContext

    fun availableBooksRepository() : AvailableBooksRepository = spring.getBean(AvailableBooksRepository::class.java)
    fun bookRepository() : BookRepository = spring.getBean(BookRepository::class.java)
    fun eventPublisher(): DomainEventPublisher = spring.getBean(DomainEventPublisher::class.java)
}