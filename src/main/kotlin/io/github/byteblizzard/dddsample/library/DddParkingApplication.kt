package io.github.byteblizzard.dddsample.library

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate

@SpringBootApplication
class DddParkingApplication

fun main(args: Array<String>) {
    runApplication<DddParkingApplication>(*args)
}

@Configuration
class config {
    @Bean
    fun transactionTemplate(platformTransactionManager: PlatformTransactionManager): TransactionTemplate {
        return TransactionTemplate(platformTransactionManager)
    }
}