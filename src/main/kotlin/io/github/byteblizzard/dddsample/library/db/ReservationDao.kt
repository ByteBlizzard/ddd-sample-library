package io.github.byteblizzard.dddsample.library.db

import io.github.byteblizzard.dddsample.library.domain.reservation.ReservationId
import io.github.byteblizzard.dddsample.library.domain.reservation.ReservationImpl
import org.springframework.data.jpa.repository.JpaRepository

interface ReservationDao: JpaRepository<ReservationImpl, ReservationId> {
    fun findByBookIdAndEffective(bookId: String, b: Boolean): ReservationImpl?
}