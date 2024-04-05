package io.github.byteblizzard.dddsample.library.domain

import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate

interface CommandInvoker {
    fun <R> invoke(run: (临时事件队列) -> R): R
}

@Component
class OneTransactionCommandInvoker(
    transactionManager: PlatformTransactionManager,
    private val domainEventDispatcher: DomainEventDispatcher
) : CommandInvoker {
    private val transactionTemplate: TransactionTemplate = TransactionTemplate(transactionManager)

    override fun <R> invoke(run: (临时事件队列) -> R): R {
        return transactionTemplate.execute { _ ->
            val eventQueue = Simple临时事件队列()
            val result = run(eventQueue)
            this.domainEventDispatcher.dispatchNow(eventQueue)

            return@execute result
        } ?: throw IllegalStateException()

    }

}