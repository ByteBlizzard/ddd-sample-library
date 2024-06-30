package io.github.byteblizzard.dddsample.library.domain.lend

import io.github.byteblizzard.dddsample.library.domain.user.UserId
import io.github.byteblizzard.dddsample.library.domain.DomainEvent
import io.github.byteblizzard.dddsample.library.domain.DomainException
import io.github.byteblizzard.dddsample.library.domain.DomainRegistry.eventPublisher
import java.time.LocalDateTime

class LendId(val value: String)

interface Lend {
    fun returnBack()
    fun reportLost()
    fun tryOverdue()
}

class LendImpl(
    val id: LendId,
    val bookId: String,
    var reportedLost: Boolean,
    var waitReturn: Boolean,
    val lendOutTime: LocalDateTime,
    val lendOutUser: UserId,
    var reportedOverdue: Boolean = false
): Lend {
    override fun returnBack() {
        if (!waitReturn) {
            return
        }

        this.waitReturn = false
        eventPublisher().publish(BookReturnedEvent(
            lendId = this.id,
            bookId = this.bookId,
            lendOutUser = lendOutUser,
            bookLostBeforeReturn = this.reportedLost
        ))

        doOverdue()
    }

    private fun overdueNow(): Boolean =  this.lendOutTime.plusMonths(1).isBefore(LocalDateTime.now())

    override fun reportLost() {
        if (this.reportedLost) {
            return
        }
        if (!waitReturn) {
            throw DomainException("书已被归还，不能上报遗失")
        }

        this.reportedLost = true
        eventPublisher().publish(BookLostEvent(
            lendId = this.id,
            bookId = this.bookId,
            lendOutUser = lendOutUser,
        ))
    }

    override fun tryOverdue() {
        if (!waitReturn) {
            return
        }

        doOverdue()
    }

    private fun doOverdue() {
        if (!this.reportedOverdue && overdueNow()) {
            this.reportedOverdue = true
            eventPublisher().publish(ReturnBookOverdueEvent(
                lendId = this.id,
                bookId = this.bookId,
                lendOutUser = lendOutUser,
            ))
        }
    }
}

interface LendRepository {
    fun save(lend: Lend)
    fun update(lend: Lend)
    fun findLatestByBookIdOrError(bookId: String): Lend
    fun findByIdOrError(lendId: LendId): Lend
}

class BookReturnedEvent(
    val lendId: LendId,
    val bookId: String,
    val lendOutUser: UserId,
    val bookLostBeforeReturn: Boolean
): DomainEvent

class ReturnBookOverdueEvent(
    val lendId: LendId,
    val bookId: String,
    val lendOutUser: UserId
): DomainEvent

class BookLostEvent(
    val lendId: LendId,
    val bookId: String,
    val lendOutUser: UserId
): DomainEvent
