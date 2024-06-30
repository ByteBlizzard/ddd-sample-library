package io.github.byteblizzard.dddsample.library.domain.reservation

import io.github.byteblizzard.dddsample.library.db.ReservationDao
import io.github.byteblizzard.dddsample.library.domain.DomainException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ReservationRepositoryImpl(
    private val reservationDao: ReservationDao
): ReservationRepository {
    override fun findByIdOrError(id: ReservationId): Reservation {
        return this.reservationDao.findByIdOrNull(id) ?: throw DomainException("找不到预定,id: ${id.value}")
    }

    override fun findEffectiveByBookId(bookId: String): Reservation? {
        return this.reservationDao.findByBookIdAndEffective(bookId, true)
    }

    override fun save(reservation: Reservation) {
        this.reservationDao.save(reservation as? ReservationImpl ?: throw IllegalArgumentException())
    }
}