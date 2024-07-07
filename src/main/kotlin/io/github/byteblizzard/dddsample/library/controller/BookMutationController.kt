package io.github.byteblizzard.dddsample.library.controller

import io.github.byteblizzard.dddsample.library.domain.book.*
import jakarta.transaction.Transactional
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Service
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate

@Controller
class BookMutationController(
    private val stockInBookCmdHandlerTx: StockInBookCmdHandlerTx,
    private val putOnShelfCmdHandler: PutOnShelfCmdHandler,
    transactionManager: PlatformTransactionManager,
    private val takeOffShelfHandler: TakeOffShelfHandler
) {
    private val transactionTemplate = TransactionTemplate(transactionManager)

    @MutationMapping
    fun stockInBook(@Argument("book") bookForm: BookForm): String {
        stockInBookCmdHandlerTx.handle(StockInBookCmd (
            bookId = bookForm.bookId,
            isbn = bookForm.isbn,
            name = bookForm.name,
            picture = bookForm.picture,
            description = bookForm.description
        ))
        return "OK"
    }

    @MutationMapping
    fun putOnShelf(@Argument("bookId") bookId: String): String {
        transactionTemplate.execute {
            putOnShelfCmdHandler.handle(PutOnShelfCmd(bookId))
        }
        return "OK"
    }

    @MutationMapping
    fun takeOffShelf(@Argument("bookId") bookId: String): String {
        transactionTemplate.execute {
            takeOffShelfHandler.handle(TakeOffShelfCmd(bookId))
        }
        return "OK"
    }
}

@Service
class StockInBookCmdHandlerTx(
    private val stockInBookCmdHandler: StockInBookCmdHandler
) {
    @Transactional
    fun handle(cmd: StockInBookCmd) {
        stockInBookCmdHandler.handle(cmd)
    }
}

data class BookForm(
    val bookId: String,
    val isbn: String,
    val name: String,
    val picture: String,
    val description: String
)