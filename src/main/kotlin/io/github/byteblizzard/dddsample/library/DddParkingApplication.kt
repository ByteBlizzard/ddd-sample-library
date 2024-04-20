package io.github.byteblizzard.dddsample.library

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@SpringBootApplication
class DddParkingApplication

fun main(args: Array<String>) {
    runApplication<DddParkingApplication>(*args)
}

@Configuration
class config {
}