package io.github.byteblizzard.dddsample.library.domain.lend

import io.github.byteblizzard.dddsample.library.domain.book.BookRepository
import io.github.byteblizzard.dddsample.library.domain.user.UserId
import io.github.byteblizzard.dddsample.library.domain.user.UserRepository
import io.github.byteblizzard.dddsample.library.domain.occupybooks.OccupyBooksRepository
import io.github.byteblizzard.dddsample.library.domain.booking.ReservationRepository
import io.github.byteblizzard.dddsample.library.domain.DomainEvent
import io.github.byteblizzard.dddsample.library.domain.DomainException
import io.github.byteblizzard.dddsample.library.domain.DomainRegistry.eventPublisher
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class LendFactory (
    private val userRepository: UserRepository,
    private val bookRepository: BookRepository,
    private val reservationRepository: ReservationRepository,
    private val occupyBooksRepository: OccupyBooksRepository,
    private val lendIdGenerator: LendIdGenerator
) {
    fun create(cmd: LendOutCmd): Lend {
        val user = userRepository.findByIdOrError(cmd.lendOutUserId)
        if (user.suspended) {
            throw DomainException("用户账户已暂停，不能借书")
        }

        val book = bookRepository.findByIdOrError(cmd.bookId)
        if (!book.isOnShelf()) {
            throw DomainException("书已下架，不能借出")
        }

        val reservation = this.reservationRepository.findEffectiveByBookId(cmd.bookId)
        if (reservation != null && reservation.reserveUserId != cmd.lendOutUserId) {
            throw DomainException("书已被别人预定，不能借出")
        }

        val occupyBooks = this.occupyBooksRepository.findByIdOrError(cmd.lendOutUserId)
        if (occupyBooks.countOfOccupiedBooks() >= 3) {
            throw DomainException("用户已经占用3本书，不能借出更多书")
        }

        val lend = LendImpl(
            id = lendIdGenerator.nextLendId(),
            bookId = cmd.bookId,
            reportedLost = false,
            waitReturn = true,
            lendOutTime = LocalDateTime.now(),
            lendOutUser = cmd.lendOutUserId
        )

        eventPublisher().publish(BookLentOutEvent(
            lendId = lend.id,
            bookId = cmd.bookId,
            lendOutUserId = cmd.lendOutUserId,
            lendOutTime = lend.lendOutTime,
            dueDate = lend.lendOutTime.plusDays(30)
        ))

        return lend
    }
}

interface LendIdGenerator {
    fun nextLendId(): LendId
}

class BookLentOutEvent(
    val lendId: LendId,
    val bookId: String,
    val lendOutUserId: UserId,
    val lendOutTime: LocalDateTime,
    val dueDate: LocalDateTime
): DomainEvent