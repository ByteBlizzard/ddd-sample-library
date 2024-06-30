package io.github.byteblizzard.dddsample.library.domain.reservation

import io.github.byteblizzard.dddsample.library.domain.user.UserId
import io.github.byteblizzard.dddsample.library.domain.DomainEvent
import io.github.byteblizzard.dddsample.library.domain.DomainRegistry.eventPublisher
import io.github.byteblizzard.dddsample.library.domain.DomainRegistry.bookRepository
import io.github.byteblizzard.dddsample.library.domain.DomainRegistry.availableBooksRepository
import jakarta.persistence.*
import java.time.LocalDateTime

interface Reservation {
    fun tryOverdue()
    fun cancel(cmd: CancelReservationCmd)
    val reserveUserId: UserId
}

class ReservationId(
    @Column(name = "id")
    val value: String
)

@Entity(name = "Reservation")
@Table(name = "t_reservation")
@AttributeOverrides( value = [
    AttributeOverride(name = "reserveUserId.value", column = Column(name = "reserveUserId"))
])
class ReservationImpl(
    @EmbeddedId
    val id: ReservationId,
    val reserveTime: LocalDateTime,

    @Embedded
    override val reserveUserId: UserId,

    val bookId: String,
    var effective: Boolean
): Reservation {
    override fun tryOverdue() {
        if (!effective) {
            return
        }

        if (this.reserveTime.plusHours(24).isBefore(LocalDateTime.now())) {
            this.effective = false

            eventPublisher().publish(ReservationOverdueEvent(
                id = this.id,
                bookId = this.bookId,
                reserveUserId = this.reserveUserId
            ))
        }
    }

    override fun cancel(cmd: CancelReservationCmd) {
        if (!effective) {
            return
        }

        this.effective = false
        eventPublisher().publish(ReservationCanceledEvent(
            id = this.id,
            bookId = this.bookId,
            reserveUserId = this.reserveUserId
        ))

        val availableBooks = availableBooksRepository().findByIdOrError(bookRepository().findByIdOrError(this.bookId).isbn)
        availableBooks.add(this.bookId)
        availableBooksRepository().save(availableBooks)
    }
}

interface ReservationRepository {
    fun findByIdOrError(id: ReservationId): Reservation
    fun findEffectiveByBookId(bookId: String): Reservation?
    fun save(reservation: Reservation)
}

class ReservationOverdueEvent(
    val id: ReservationId,
    val bookId: String,
    val reserveUserId: UserId
): DomainEvent

class ReservationCanceledEvent(
    val id: ReservationId,
    val bookId: String,
    val reserveUserId: UserId
): DomainEvent
