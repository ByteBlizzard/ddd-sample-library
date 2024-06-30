package io.github.byteblizzard.dddsample.library.domain.reservation

import io.github.byteblizzard.dddsample.library.domain.availablebooks.AvailableBooksRepository
import io.github.byteblizzard.dddsample.library.domain.user.UserId
import io.github.byteblizzard.dddsample.library.domain.user.UserRepository
import io.github.byteblizzard.dddsample.library.domain.occupybooks.OccupyBooksRepository
import io.github.byteblizzard.dddsample.library.domain.DomainEvent
import io.github.byteblizzard.dddsample.library.domain.DomainException
import io.github.byteblizzard.dddsample.library.domain.DomainRegistry.eventPublisher
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ReservationFactory(
    private val reservationIdGenerator: ReservationIdGenerator,
    private val userRepository: UserRepository,
    private val availableBooksRepository: AvailableBooksRepository,
    private val occupyBooksRepository: OccupyBooksRepository
) {
    fun create(reserveCmd: ReserveCmd): Reservation {
        val reserveUser = this.userRepository.findByIdOrError(reserveCmd.reserveUser)
        if (reserveUser.suspended) {
            throw DomainException("预定人账户已禁用，不可预定书")
        }

        val availableBooks = this.availableBooksRepository.findByIdOrError(reserveCmd.isbn)
        if (availableBooks.isEmpty()) {
            throw DomainException("没有可预订的书了")
        }

        if (this.occupyBooksRepository.findByIdOrError(reserveCmd.reserveUser).countOfOccupiedBooks() >= 3) {
            throw DomainException("每个用户最多占用3本书，你已达到限制，不能再预定啦")
        }

        val bookToReserve = availableBooks.chooseBookRandomly()

        val reservation = ReservationImpl(
            id = reservationIdGenerator.nextReservationId(),
            reserveTime = LocalDateTime.now(),
            reserveUserId = reserveCmd.reserveUser,
            bookId = bookToReserve,
            effective = true
        )

        eventPublisher().publish(BookReservedEvent(
            reservationId = reservation.id,
            bookId = reservation.bookId,
            reserveUserId = reservation.reserveUserId,
            reserveTime = reservation.reserveTime
        ))

        return reservation
    }
}

interface ReservationIdGenerator {
    fun nextReservationId(): ReservationId
}

class BookReservedEvent(
    val reservationId: ReservationId,
    val bookId: String,
    val reserveUserId: UserId,
    val reserveTime: LocalDateTime
) : DomainEvent
