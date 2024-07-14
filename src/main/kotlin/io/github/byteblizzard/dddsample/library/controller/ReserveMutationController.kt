package io.github.byteblizzard.dddsample.library.controller

import io.github.byteblizzard.dddsample.library.domain.reservation.*
import io.github.byteblizzard.dddsample.library.domain.user.UserId
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import org.springframework.transaction.support.TransactionTemplate

@Controller
class ReserveMutationController(
    private val reserveBookCmdHandler: ReserveCmdHandler,
    private val cancelReservationCmdHandler: CancelReservationCmdHandler,
    private val transactionTemplate: TransactionTemplate
) {
    @MutationMapping
    fun reserveBook(
        @Argument("isbn") isbn: String,
        @Argument("userId") userId: String
    ): String {
        transactionTemplate.execute {
            reserveBookCmdHandler.handle(ReserveCmd(isbn = isbn, reserveUser = UserId(userId)))
        }
        return "OK"
    }

    @MutationMapping
    fun cancelReservation(
        @Argument("reservationId") reservationId: String
    ): String {
        transactionTemplate.execute {
            cancelReservationCmdHandler.handle(CancelReservationCmd(ReservationId(reservationId)))
        }
        return "OK"
    }
}